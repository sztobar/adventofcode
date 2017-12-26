(ns adventofcode2017.day23
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))


(defn get-input
  ([] (get-input "2017/day23.data"))
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


(defn execute-set [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (get-expr val reg))))


(defn execute-sub [[addr val] {:keys [line reg] :as state}]
  (assoc state
         :line (inc line)
         :reg (assoc reg addr (- (get reg addr 0)
                                 (get-expr val reg)))))


(defn execute-mul [[addr val] {:keys [line reg mul] :as state}]
  (assoc state
         :mul (inc mul)
         :line (inc line)
         :reg (assoc reg addr (* (get reg addr 0)
                                 (get-expr val reg)))))


(defn execute-jnz [[addr offset] {:keys [line reg] :as state}]
  (assoc state
         :line (if (not= (get-expr addr reg) 0)
                 (+ line (get-expr offset reg))
                 (inc line))))


(defn parse-token [token state]
  (let [[callee & args] token]
    (case callee
      "set" (execute-set args state)
      "sub" (execute-sub args state)
      "mul" (execute-mul args state)
      "jnz" (execute-jnz args state))))


(defn parse [coll]
  (loop [state {:line 0
                :reg {}
                :mul 0}]
    (if (>= (:line state) (count coll))
      (:mul state)
      (recur (parse-token (coll (:line state)) state)))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (parse input)))
;; 5929


;; PART TWO


;; PROGRAM ANALYSIS
;; some pseudo lisp
`[
(set a 1)
(set b 79)
(set c b)
(mul b 100)
(sub b -100000)
(set c b)
(sub c -17000)
; state {:a 1 :b 107 900 :c 124 900 :f 1 :d 2}
(do
  (set f 1)
  (set d 2)
  (do
    (set e 2)
    (do
      (set g d)
      (mul g e)
      (sub g b)
      (if (= 0 g); g = (d * e) - b
        (set f 0)); only when b isnt prime
      (sub e -1)
      (set g e)
      (sub g b)
      (while (not= g 0))); g = e - b; e++
    ; state {:a 1 :b 107 900 :c 124 900 :f 0 :d <2 - 107 900> g: 0 :e <2 - 53 950>}
    (sub d -1)
    (set g d)
    (sub g b)
    (while (not= g 0))); g = d - b; d++
  ; state {:a 1 :b 107 900 :c 124 900 :f 0 :d 107 900 g: 0 :e <2 - 53 950>}
  (if (= 0 f)
    (sub h -1))
  (set g b)
  (sub g c)
  (if (= 0 g); g = b -c
    (break)
    :else
    (sub b -17)); 1000 times
  (while true))
]


(defn not-prime? [n]
  (some #(= 0 (mod n %)) (range 2 (Math/sqrt n))))

(defn ex2 []
  (->> (range 1001)
       (map #(+ (* % 17) 107900))
       (filter not-prime?)
       count))
; 907
