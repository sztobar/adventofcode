(ns adventofcode2017.day18
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input
  ([] (get-input "2017/day18.data"))
  ([filename]
   (->> (io/resource filename)
       slurp
       (#(str/split % #"\r\n"))
       (map #(str/split % #"\s"))
       vec)))


(defn get-expr [param reg]
  (try
    (Integer/parseInt param)
    (catch NumberFormatException e
      (get reg param 0))))


(defn execute-snd [[addr] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :snd (get-expr addr reg)))


(defn execute-set [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (get-expr val reg))))


(defn execute-add [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (+ (get reg addr 0)
                                 (get-expr val reg)))))


(defn execute-mul [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (* (get reg addr 0)
                                 (get-expr val reg)))))


(defn execute-mod [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (mod (get reg addr 0)
                                   (get-expr val reg)))))


(defn execute-rcv [[addr] {:keys [line snd rcv reg] :as state}]
  (assoc state
         :line (inc line)
         :rcv (if (not= (get-expr addr reg) 0)
                snd
                rcv)))


(defn execute-jgz [[addr offset] {:keys [line reg] :as state}]
  (assoc state
         :line (if (> (get-expr addr reg) 0)
                 (+ line (get-expr offset reg))
                 (inc line))))


(defn parse-token [token state]
  (let [[callee & args] token]
    (case callee
      "snd" (execute-snd args state)
      "set" (execute-set args state)
      "add" (execute-add args state)
      "mul" (execute-mul args state)
      "mod" (execute-mod args state)
      "rcv" (execute-rcv args state)
      "jgz" (execute-jgz args state))))


(defn parse [coll]
  (loop [state {:line 0
                :reg {}
                :snd nil
                :rcv nil}]
    (if (some? (:rcv state))
      (:rcv state)
      (recur (parse-token (coll (:line state)) state)))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (parse input)))
;; 7071


;; PART TWO


(defn execute-send [[expr] {:keys [line send reg counter] :as state}]
  (assoc state
         :send (conj send (get-expr expr reg))
         :counter (inc counter)
         :line (inc line)))


(defn execute-receive [[addr] {:keys [line send reg] :as state} program]
  (if (> (count (:send program)) 0)
    (assoc state
           :receive :obtained
           :reg (assoc reg addr (first (:send program)))
           :line (inc line))
    (assoc state
           :receive :pending)))


(defn next-step [p line p']
  (let [[callee & args] line]
    (case callee
      "end" p
      "snd" (execute-send args p)
      "set" (execute-set args p)
      "add" (execute-add args p)
      "mul" (execute-mul args p)
      "mod" (execute-mod args p)
      "rcv" (execute-receive args p p')
      "jgz" (execute-jgz args p))))


(defn check-for-received [p0 p1]
  (if (= (:receive p0) :obtained)
    [(dissoc p0 :receive)
     (assoc p1 :send (vec (rest (:send p1))))]
    [p0 p1]))


(defn check-p0-received [[p0 p1]]
  (check-for-received p0 p1))


(defn check-p1-received [[p0 p1]]
  (vec
   (reverse
    (check-for-received p1 p0))))


(defn two-programs-start [coll]
  (loop [p0 {:reg {"p" 0} :line 0 :send [] :counter 0}
         p1 {:reg {"p" 1} :line 0 :send [] :counter 0}]
    (if (and (or (= (:receive p0) :pending) (= (:line p0) (count coll)))
             (or (= (:receive p1) :pending) (= (:line p1) (count coll))))
      (:counter p1)
      (let [[p0 p1] (-> [p0 p1]
                        (check-p0-received)
                        (check-p1-received))]
        (recur (next-step p0 (coll (:line p0)) p1)
               (next-step p1 (coll (:line p1)) p0))))))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (-> (conj input ["end"])
       (two-programs-start))))
;; 8001
