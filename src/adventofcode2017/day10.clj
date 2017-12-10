(ns adventofcode2017.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def list-size 256)


(defn get-input [filename]
  (slurp (io/resource filename)))


(defn parse-input [input]
  (mapv #(Integer/parseInt %)
        (str/split input #",")))


(defn duplicate [coll]
  (vec (concat coll coll)))


(defn apply-knot-hash [list pos length]
  (let [end (+ pos length)]
    (if (> end list-size)
      (let [offset (- end list-size)
            split-p (- length offset)
            sublist (subvec (duplicate list) pos end)
            reversed (vec (reverse sublist))]
        (vec (concat (subvec reversed split-p)
                     (subvec list offset pos)
                     (subvec reversed 0 split-p))))
      (vec (concat (subvec list 0 pos)
                   (reverse (subvec list pos end))
                   (subvec list end))))))


(defn knot-hash [{:keys [list pos skip]} length]
  {:list (apply-knot-hash list pos length)
   :pos (mod (+ length pos skip) list-size)
   :skip (inc skip)})


(defn start-knot-hash [coll]
  (:list (reduce knot-hash
                 {:list (vec (range list-size))
                  :pos 0
                  :skip 0}
                 coll)))


(defn multiply-first-two [list]
  (apply * (take 2 list)))


(defn ex1
  ([]
   (ex1 (get-input "2017/day10.data")))
  ([input]
   (->> input
        parse-input
        start-knot-hash
        multiply-first-two)))


;; PART TWO


(defn convert-to-ascii-vec [str]
  (mapv int (vec str)))


(defn add-suffix [coll]
  (vec (concat coll [17 31 73 47 23])))


(defn clone-64-times [coll]
  (flatten (take 64 (repeat coll))))


(defn xorify [coll]
  (map #(apply bit-xor %) coll))


(defn hexify [coll]
  (map #(let [hex (Integer/toHexString %)]
          (if (= (count hex) 1)
            (str \0 hex)
            hex))
       coll))


(defn ex2
  ([]
   (ex2 (get-input "2017/day10.data")))
  ([input]
   (->> input
        convert-to-ascii-vec
        add-suffix
        clone-64-times
        start-knot-hash
        (partition 16)
        xorify
        hexify
        str/join)))
