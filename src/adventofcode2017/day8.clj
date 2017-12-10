(ns adventofcode2017.day8
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))


(defn apply-condition [condition reg]
  (let [[key cond val] condition
        val (Integer/parseInt val)
        record (get reg key 0)]
    (case cond
      ">" (> record val)
      ">=" (>= record val)
      "<" (< record val)
      "<=" (<= record val)
      "==" (= record val)
      "!=" (not= record val))))


(defn inc-command [val]
  (fn [reg] (+ reg val)))


(defn dec-command [val]
  (fn [reg] (- reg val)))


(defn apply-command [command reg]
  (let [[key comm val] command
        val (Integer/parseInt val)
        record (get reg key 0)]
    (case comm
      "inc" (+ record val)
      "dec" (- record val))))


(defn parse-line [line]
  (let [[record command value _ & condition] (str/split line #"\s")]
    {:command [record command value]
     :condition condition}))


(defn apply-action [register action]
  (let [{:keys [condition command]} action
        [key] command]
    (if (apply-condition condition register)
      (assoc register key (apply-command command register))
      register)))


(defn get-input [filename]
  (-> (io/resource filename)
      io/reader
      line-seq))


(defn ex1
  ([]
   (ex1 "2017/day8.data"))
  ([filename]
   (->> (get-input filename)
        (map parse-line)
        (reduce apply-action {})
        vals
        (#(apply max %)))))


;; PART TWO


(defn apply-action-with-max-watch [agg action]
  (let [{:keys [register reg-max]} agg
        {:keys [condition command]} action
        [key] command]
    (if (apply-condition condition register)
      (let [val (apply-command command register)]
        {:register (assoc register key val)
         :reg-max (max reg-max val)})
      agg)))



(defn ex2
  ([]
   (ex2 "2017/day8.data"))
  ([filename]
   (->> (get-input filename)
        (map parse-line)
        (reduce apply-action-with-max-watch {:register {} :reg-max 0})
        :reg-max)))
