(ns adventofcode2017.day19
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn transpose [coll]
  (let [height (count coll)
        width (count (coll 0))]
    (mapv (fn [x]
            (mapv (fn [y]
                    (get-in coll [y x]))
                  (range height)))
          (range width))))


(defn get-input
  ([] (get-input "2017/day19.data"))
  ([filename]
   (->> (io/resource filename)
       (slurp)
       (#(str/split % #"\n"))
       (mapv #(str/split % #""))
       transpose)))


(defn map-beggining [coll]
  [(first (filter #(= (get-in coll [% 0]) "|") (range (count coll))))
   0])


(def dir-u [ 0 -1])
(def dir-d [ 0  1])
(def dir-l [-1  0])
(def dir-r [ 1  0])
(def dirs-h [dir-u dir-d])
(def dirs-v [dir-l dir-r])


(defn add-dir [pos dir]
  (mapv + pos dir))


(defn letter? [str]
  (let [c (int (get str 0))]
    (and (>= c 65) ;; A ASCII
         (<= c 90)))) ;; Z ASCII


(defn new-dir-after [coll pos dir]
  (let [check-dirs ({dir-u dirs-v
                     dir-d dirs-v
                     dir-l dirs-h
                     dir-r dirs-h} dir)]
    (first (filter #(not= (get-in coll (add-dir pos %)) " ")
                   check-dirs))))


(defn start-traversing [coll]
  (loop [pos (map-beggining coll)
         dir dir-d
         letters []
         steps 0]
    (let [cell (get-in coll pos)]
      (cond (= cell " ") {:letters letters :steps steps}
            (letter? cell) (recur (mapv + pos dir) dir (conj letters cell) (inc steps))
            (= cell "+") (let [new-dir (new-dir-after coll pos dir)]
                           (recur (mapv + pos new-dir) new-dir letters (inc steps)))
            :else (recur (mapv + pos dir) dir letters (inc steps))))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (-> (start-traversing input)
       :letters
       str/join)))
;; GEPYAWTMLK


;; PART TWO


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (-> (start-traversing input)
       :steps)))
;; 17628
