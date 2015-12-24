(ns adventofcode.sixth
  (:require [adventofcode.file :refer :all]
            [clojure.set :refer :all])
  (:use [clojure.java.io]))

(defn parse-coord [s]
  (let [[_ x y] (re-matches #"(\d+),(\d+)" s)]
    [(Integer/parseInt x) (Integer/parseInt y)]))

(def command-regex #"^(\w+\s?\w+)\s(\d+,\d+)\s\w+\s(\d+,\d+)")


;; TRANSIENT BANG! THE ONLY RIGHT WAY!
;; input6.1 "Elapsed time: 12967.1239 msecs"

(defn use-command! [command coll p]
  (cond
    (and (coll p) (or (= command "toggle") (= command "turn off"))) (disj! coll p)
    (and (nil? (coll p)) (or (= command "toggle") (= command "turn on"))) (conj! coll p)))

(defn order! [s coll]
  (let [[_ command from to] (re-matches command-regex s)
        [x1 y1] (parse-coord from)
        [x2 y2] (parse-coord to)]
    (doseq [x (range x1 (inc x2))
            y (range y1 (inc y2))]
      (use-command! command coll [x y]))
    coll))

(defn read-orders! [f]
  (count
    (persistent!
      (file-reduce f #(order! %2 %1) (transient #{})))))


;; TRANSIENT BANG! BRIGTHNESS MODE
;; input6.1 "Elapsed time: 16958.677159 msecs"

(defn use-brigthness-command! [command coll p]
  (let [v (get coll p 0)]
    (cond
      (and (> v 0) (= command "turn off")) (assoc! coll p (dec v))
      (= command "turn on") (assoc! coll p (inc v))
      (= command "toggle") (assoc! coll p (+ 2 v))
      :else coll)))

(defn brigthness-order! [s coll]
  (let [[_ command from to] (re-matches command-regex s)
        [x1 y1] (parse-coord from)
        [x2 y2] (parse-coord to)
        f (partial use-brigthness-command! command)]
    (loop [x x1
           y y1
           coll coll]
      (if (= x x2)
        (if (= y y2)
          (f coll [x y])
          (recur x1 (inc y) (f coll [x y])))
        (recur (inc x) y (f coll [x y]))))))

(defn read-brigthness! [f]
  (reduce (fn [r [k v]] (+ r v)) 0 
    (persistent!
      (file-reduce f #(brigthness-order! %2 %1) (transient {})))))

(defn get-brigthness! [s]
  (reduce (fn [r [k v]] (+ r v)) 0 
    (persistent!
      (brigthness-order! s (transient {})))))

;; NO BANG, SAME "WAY"
;; input6.1 "Elapsed time: 19695.69602 msecs"

(defn use-command [command coll p]
  (cond
    (and (coll p) (or (= command "toggle") (= command "turn off"))) (disj coll p)
    (and (nil? (coll p)) (or (= command "toggle") (= command "turn on"))) (conj coll p)
    :else coll))

(defn rect-vec [[x1 y1] [x2 y2]]
  (vec
    (for [x (range x1 (inc x2))
          y (range y1 (inc y2))]
      [x y])))

(defn order [s coll]
  (let [[_ command from to] (re-matches command-regex s)
        from' (parse-coord from)
        to' (parse-coord to)
        rect (rect-vec from' to')]
    (reduce #(use-command command %1 %2) coll rect)))

(defn read-orders [f]
  (count
    (file-reduce f #(order %2 %1) #{})))
