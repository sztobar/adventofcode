(ns adventofcode2017.day13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input
  ([] (get-input "2017/day13.data"))
  ([filename]
   (->> (io/resource filename)
        io/reader
        line-seq)))


(defn parse-input [lines]
  (reduce #(let [[k v] (str/split %2 #": ")
                 k (Integer/parseInt k)
                 v (Integer/parseInt v)]
             (assoc %1 k {:range v :pos 0 :dir 1}))
          {}
          lines))


(defn firewall-step [firewall]
  (reduce
   (fn [agg [key {:keys [range pos dir]}]]
     (assoc agg key
            (cond
              (= pos (dec range))
              {:range range :pos (dec pos) :dir -1}
              (= pos 0)
              {:range range :pos (inc pos) :dir 1}
              :else
              {:range range :pos (+ pos dir) :dir dir})))
   {}
   firewall))


(defn caught-cost [pos firewall]
  (if-let [firewall-layer (firewall pos)]
    (if (= 0 (:pos firewall-layer))
      (* pos (:range firewall-layer))
      0)
    0))


(defn next-step [{:keys [firewall severity]} pos]
  {:firewall (firewall-step firewall)
   :severity (+ severity
                (caught-cost pos
                             firewall))})


(defn cross-firewall [firewall]
  (reduce next-step
          {:firewall firewall :severity 0}
          (range (inc (apply max (keys firewall))))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        cross-firewall
        :severity)))
;; 1300


;; PART 2


(defn next-step-cautious [{:keys [firewall keys]} pos]
  {:caught (if-let [firewall-layer (get firewall pos)]
             (= 0 (:pos firewall-layer)))
   :firewall (firewall-step firewall)})


(defn noticed? [firewall]
  (->> (range (inc (apply max (keys firewall))))
       (reductions next-step-cautious {:firewall firewall :caught false})
       (filter :caught)
       empty?
       not))


(defn delay-until-unnoticed-pass [firewall]
  (loop [delay 0
         firewall (nth (iterate firewall-step firewall) 0)]
    (if (noticed? firewall)
      (recur (inc delay) (firewall-step firewall))
      delay)))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (parse-input input)
        delay-until-unnoticed-pass')))
;; I am disgrace to my clan
;; Elapsed time: 192606.296273 msecs
;; 3870382
