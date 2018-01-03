(ns adventofcode2017.day25
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input
  ([] (get-input "2017/day25.data"))
  ([filename]
   (->> (io/resource filename)
        slurp
        (#(str/split % #"\r\n"))
        (filter #(not= % "")))))


(defn parse-state-name [txt]
  (-> (re-find #"state (\w)" txt)
      second))


(defn parse-steps [txt]
  (-> (re-find #"after (\d+)" txt)
      second
      (#(Integer/parseInt %))))


(defn parse-write [txt]
  (-> (re-find #"value (\d)" txt)
      second
      (#(Integer/parseInt %))))


(defn parse-move [txt]
  (-> (re-find #"to the (\w+)" txt)
      second
      (#(if (= "left" %) -1 1))))


(defn parse-state [coll]
  (let [coll (vec coll)]
    {:state (parse-state-name (get coll 0))
     :zero {:write (parse-write (get coll 2))
            :move (parse-move (get coll 3))
            :continue (parse-state-name (get coll 4))}
     :one {:write (parse-write (get coll 6))
           :move (parse-move (get coll 7))
           :continue (parse-state-name (get coll 8))}}))


(defn parse-states [coll]
  (->> (partition 9 coll)
       (map parse-state)
       (reduce (fn [agg {:keys [state zero one]}]
                 (assoc agg state [zero one]))
               {})))


(defn parse-input [coll]
  (let [[begin steps & rest] coll]
    {:begin (parse-state-name begin)
     :steps (parse-steps steps)
     :states (parse-states rest)}))


(defn apply-state [tape pos state]
  (let [val (if (contains? tape pos) 1 0)
        {:keys [write move continue]} (get state val)]
    {:tape (if (= write 0)
             (disj tape pos)
             (conj tape pos))
     :pos (+ pos move)
     :state continue}))


(defn run-turing [{:keys [begin steps states]}]
  (loop [i 0
         pos 0
         tape #{}
         state begin]
    (if (>= i steps)
      tape
      (let [{:keys [tape pos state]} (apply-state tape pos (get states state))]
        (recur (inc i)
               pos
               tape
               state)))))


(defn diagnose [coll]
  (count coll))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        run-turing
        diagnose)))
