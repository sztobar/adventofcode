(ns adventofcode2016.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def go-map
  {:1 {:u :1
       :l :1
       :d :4
       :r :2}
   :2 {:u :2
       :l :1
       :d :5
       :r :3}
   :3 {:u :3
       :l :2
       :d :6
       :r :3}
   :4 {:u :1
       :l :4
       :d :7
       :r :5}
   :5 {:u :2
       :l :4
       :d :8
       :r :6}
   :6 {:u :3
       :l :5
       :d :9
       :r :6}
   :7 {:u :4
       :l :7
       :d :7
       :r :8}
   :8 {:u :5
       :l :7
       :d :8
       :r :9}
   :9 {:u :6
       :l :8
       :d :9
       :r :9}})

(def dir-decode
  {\U :u
   \L :l
   \D :d
   \R :r})

(defn parse-input [input]
  (->> (str/split input #"[\r\n]+")
       (map vec)
       (map #(map dir-decode %))
       (map vec)
       vec))

(defn decode-line [{:keys [pos out]} dirs]
  (let [click (reduce (fn [p n] (get-in go-map [p n])) pos dirs)]
    {:pos click
     :out (conj out click)}))

(defn decode-bathroom-code [inputs]
  (reduce decode-line {:pos :5 :out []} inputs))

(defn ex1 []
  (->> (slurp (io/resource "day2.data"))
       (parse-input)
       (decode-bathroom-code)
       :out
       (map name)
       (str/join)))

;; EXERCISE TWO

(def go-map2
  {:1 {:u :1
       :l :1
       :d :3
       :r :1}
   :2 {:u :2
       :l :2
       :d :6
       :r :3}
   :3 {:u :1
       :l :2
       :d :7
       :r :4}
   :4 {:u :4
       :l :3
       :d :8
       :r :4}
   :5 {:u :5
       :l :5
       :d :5
       :r :6}
   :6 {:u :2
       :l :5
       :d :A
       :r :7}
   :7 {:u :3
       :l :6
       :d :B
       :r :8}
   :8 {:u :4
       :l :7
       :d :C
       :r :9}
   :9 {:u :9
       :l :8
       :d :9
       :r :9}
   :A {:u :6
       :l :A
       :d :A
       :r :B}
   :B {:u :7
       :l :A
       :r :C
       :d :D}
   :C {:u :8
       :l :B
       :d :C
       :r :C}
   :D {:u :B
       :l :D
       :d :D
       :r :D}})

(defn decode-line2 [{:keys [pos out]} dirs]
  (let [click (reduce (fn [p n] (get-in go-map2 [p n])) pos dirs)]
    {:pos click
     :out (conj out click)}))

(defn decode-bathroom-code2 [inputs]
  (reduce decode-line2 {:pos :5 :out []} inputs))

(defn ex2 []
  (->> (slurp (io/resource "day2.data"))
       (parse-input)
       (decode-bathroom-code2)
       :out
       (map name)
       (str/join)))
