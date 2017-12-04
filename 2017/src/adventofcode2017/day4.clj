(ns adventofcode2017.day4
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))


(defn get-input []
  (slurp (io/resource "day4.data")))


(defn line-to-words [line]
  (str/split line #"\s"))


(defn uniq? [coll]
  (->> coll
       (reductions
        (fn [coll v]
          (if (coll v)
            false
            (conj coll v)))
        #{})
       (filter false?)
       first
       nil?))


(defn is-valid? [line]
  (uniq? (line-to-words line)))


(defn ex1 []
  (->> (get-input)
       (#(str/split % #"\n"))
       (map is-valid?)
       (filter true?)
       count))


(defn word-to-char-map [word]
  (reduce
   (fn [coll c]
     (assoc coll c (inc (get coll c 0))))
   {}
   (str/split word #"")))


(defn has-anagram? [line]
  (->> (line-to-words line)
       (map word-to-char-map)
       uniq?
       not))


(defn ex2 []
  (->> (get-input)
       (#(str/split % #"\n"))
       (map has-anagram?)
       (filter false?)
       count))
