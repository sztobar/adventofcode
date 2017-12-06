(ns adventofcode2016.day1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def turn-map
  {:l {:n :w
       :w :s
       :s :e
       :e :n}
   :r {:n :e
       :e :s
       :s :w
       :w :n}
   :i {:n :n ; identity
       :e :e
       :s :s
       :w :w}})

(defmulti go (fn [dir _ _] dir))

(defmethod go :n [_ {:keys [x y]} steps]
  {:x x
   :y (- y steps)})

(defmethod go :e [_ {:keys [x y]} steps]
  {:x (+ x steps)
   :y y})

(defmethod go :s [_ {:keys [x y]} steps]
  {:x x
   :y (+ y steps)})

(defmethod go :w [_ {:keys [x y]} steps]
  {:x (- x steps)
   :y y})

(defn go-to [{:keys [pos dir]} {:keys [turn steps]}]
  (let [new-dir (get-in turn-map [turn dir])
        new-pos (go new-dir pos steps)]
    {:pos new-pos
     :dir new-dir}))

(def dir-decode
  {"R" :r
   "L" :l})

(defn parse-input [data]
  (->> (str/split data #",?[\s\r\n]")
       (map #(let [[_ dir steps] (re-matches #"([RL])(\d+)" %)]
               {:turn (dir-decode dir)
                :steps (Integer/parseInt steps)}))))

(defn find-easter-bunny-hq [inputs]
  (reduce go-to {:pos {:x 0 :y 0} :dir :n} inputs))

(defn abs [n]
  (if (neg? n)
    (- n)
    n))

(defn exercise [input]
  (let [{{:keys [x y]} :pos} (find-easter-bunny-hq (parse-input input))]
    (+ (abs x) (abs y))))

(defn ex1 []
  (exercise (slurp (io/resource "day1.data"))))

;; PART TWO

(defn find-easter-bunny-hq-when-visit-twice [inputs]
  (reduce (fn [{:keys [pos dir visited]} step]
            (let [new-pos-dir (go-to {:pos pos :dir dir} step)]
              (if (visited (:pos new-pos-dir))
                (reduced new-pos-dir)
                (assoc new-pos-dir :visited (conj visited (:pos new-pos-dir))))))
          {:pos {:x 0 :y 0} :dir :n :visited #{}}
          inputs))

(defn parse-input-two [input]
  (reduce (fn [acc {:keys [turn steps]}]
            (let [micro-steps (map (fn [_] {:turn :i :steps 1}) (range (- steps 1)))]
              (concat acc [{:turn turn :steps 1}] micro-steps)))
          []
          (parse-input input)))

(defn exercise-two [input]
  (let [{{:keys [x y]} :pos} (find-easter-bunny-hq-when-visit-twice (parse-input-two input))]
    (+ (abs x) (abs y))))

(defn ex2 []
  (exercise-two (slurp (io/resource "day1.data"))))
