(ns adventofcode2015.fifth
  (:require [adventofcode2015.file :refer :all]))

(def vowel-regex
  #"[aeiou]")

(defn got-vowels [s]
  (->> s
       (re-seq vowel-regex)
       (take 3)
       (count)
       (= 3)))

(def double-regex
  #"(\w)\1")

(defn got-double [s]
  (->> s
       (re-find double-regex)
       nil?
       not))

(defn got-not-prohibited [s]
  (nil? (re-find #"ab|cd|pq|xy" s)))

(defn is-nice [s]
  (and
    (got-vowels s)
    (got-double s)
    (got-not-prohibited s)))

(defn count-nice [s]
  (file-reduce s
    (fn [r i]
      (+ r (if (is-nice i) 1 0)))
    0))

(defn got-pair [s]
  (->> s
       (re-find #"(\w\w).*\1")
       nil?
       not))

(defn got-repeat [s]
  (->> s
       (re-find #"(\w).\1")
       nil?
       not))

(defn is-nice2 [s]
  (and
    (got-pair s)
    (got-repeat s)))

(defn count-nice2 [s]
  (file-reduce s
    (fn [r i]
      (+ r (if (is-nice2 i) 1 0)))
    0))
