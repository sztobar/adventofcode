(ns adventofcode2017.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input []
  (slurp (io/resource "2017/day11.data")))


(defn dir-to-coord [dir]
  (case dir
    "n"  [ 0  1 -1]
    "ne" [ 1  0 -1]
    "se" [ 1 -1  0]
    "s"  [ 0 -1  1]
    "sw" [-1  0  1]
    "nw" [-1  1  0]))


(defn parse-input [input]
  (map dir-to-coord
       (str/split input #",")))


(defn go [[x y z] [dx dy dz]]
  [(+ x dx) (+ y dy) (+ z dz)])


(defn start-path [dirs]
  (reduce go
          [0 0 0]
          dirs))


(defn abs [v] (Math/abs v))


(defn get-dist [[x y z]]
  (-> (+ (abs x)
         (abs y)
         (abs z))
      (/ 2)))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> input
        parse-input
        start-path
        get-dist)))
;; 707


;; PART TWO


(defn max-dist-count [input]
  (reduce (fn [{:keys [max-dist pos]} dir]
               (let [new-pos (go pos dir)
                     dist (get-dist new-pos)
                     max-dist (max max-dist dist)]
                 {:max-dist max-dist
                  :pos new-pos}))
          {:max-dist 0
           :pos [0 0 0]}
          input))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> input
        parse-input
        max-dist-count
        :max-dist)))
;; 1490
