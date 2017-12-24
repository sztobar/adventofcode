(ns adventofcode2017.day20
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input
  ([] (get-input "2017/day20.data"))
  ([filename]
   (->> (io/resource filename)
        io/reader
        line-seq
        vec)))


(defn parse-input [input]
  (mapv #(read-string
          (str "{"
               ":i " % " "
               (str/replace (get input %) #"(\w)=<(-?\d+,-?\d+,-?\d+)>(,\s)?" ":$1 [$2] ")
               "}"))
        (range (count input))))


(defn add-v [v1 v2]
  (mapv + v1 v2))


(defn abs [v]
  (if (neg? v)
    (* v -1)
    v))


(defn dist [[x y z]]
  (+ (abs x)
     (abs y)
     (abs z)))


(defn particle-next-step [{:keys [p v a] :as particle}]
  (let [v' (add-v v a)
        p' (add-v p v')]
    (assoc particle
           :a a
           :v v'
           :p p'
           :dist (dist p'))))


(defn start-particles [coll n]
  (loop [i 0
         particles coll]
    (if (= i n)
      particles
      (recur (inc i)
             (mapv particle-next-step particles)))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (-> (parse-input input)
       (start-particles 1000)
       (#(sort-by :dist %))
       first
       :i)))
;; 300


(defn group-by-pos [coll]
  (reduce (fn [coll particle]
            (let [pos (:p particle)]
              (if (some? (coll pos))
                (update coll pos conj particle)
                (assoc coll pos [particle]))))
          {}
          coll))


(defn start-colliding-particles [coll n]
  (loop [i 0
         particles coll]
    (if (= i n)
      particles
      (let [particles' (mapv particle-next-step particles)
            groups (group-by-pos particles')
            non-collided (flatten (filter #(= (count %) 1) (vals groups)))]
      (recur (inc i)
             non-collided)))))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (-> (parse-input input)
       (start-colliding-particles 10000)
       count)))
