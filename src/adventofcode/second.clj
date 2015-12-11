(ns adventofcode.second
  (:require [clojure.string :as str])
  (:use [clojure.java.io]))


(defn need-paper
  "Those elves"
  ([s] (apply need-paper (map (fn [n] (Integer/parseInt n)) (str/split s #"x"))))
  ([l w h]
   (let [lw (* l w)
         wh (* w h)
         hl (* h l)
         m (min lw wh hl)]
     (+ (* 2 lw) (* 2 wh) (* 2 hl) m))))

(defn need-paper-file [s]
  (with-open [rdr (reader s)]
    (reduce #(+ %1 (need-paper %2)) 0 (line-seq rdr))))

(defn need-ribbon
  "Those elves"
  ([s] (apply need-ribbon (map (fn [n] (Integer/parseInt n)) (str/split s #"x"))))
  ([l w h]
   (let [lw (+ l l w w)
         wh (+ w w h h)
         hl (+ h h l l)
         f (* l w h)
         m (min lw wh hl)]
     (+ f m))))

(defn need-ribbon-file [s]
  (with-open [rdr (reader s)]
    (reduce #(+ %1 (need-ribbon %2)) 0 (line-seq rdr))))
