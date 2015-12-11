(ns adventofcode.third)

(defn deliver-presents [way]
  (loop [way' way
         pos [0 0]
         coll #{}]
    (let [[dir & r] way'
          coll' (conj coll pos)
          [x y] pos]
      (cond
        (= dir \^) (recur r [x (inc y)] coll')
        (= dir \v) (recur r [x (dec y)] coll')
        (= dir \<) (recur r [(dec x) y] coll')
        (= dir \>) (recur r [(inc x) y] coll')
        (nil? dir) (reduce (fn [r i] (inc r)) 0 coll')))))

(defn take-turn [p s]
  (let [dir (first s)
        [x y] p]
    (cond
      (= dir \^) [x (inc y)]
      (= dir \v) [x (dec y)]
      (= dir \<) [(dec x) y]
      (= dir \>) [(inc x) y]
      (nil? dir) nil)))


(defn robo-santa [s]
  (loop [way s
         pos [0 0]
         robo-pos [0 0]
         coll #{[0 0]}
         robo? false]
    (let [pos (if robo? pos (take-turn pos way))
          robo-pos (if robo? (take-turn robo-pos way) robo-pos)
          p' (if robo? robo-pos pos)]
      (if (nil? p')
        (count coll)
        (recur (rest way) pos robo-pos (conj coll p') (not robo?))))))

