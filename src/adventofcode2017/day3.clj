(ns adventofcode2017.day3
  (:import [java.lang.Math]))


(defn sqr [n] (* n n))


;; edge of n is part of the spiral with range <(n^2+1) : (n+1)^2>
(defn find-ring-edge [n]
  (loop [edge 0]
    (if (> n (sqr (inc edge)))
      (recur (inc edge))
      edge)))


;; every ring consist of 2 edges
;;
;; ring with value of n is n steps
;; from the center in straight line
(defn get-ring-for-edge [edge]
  (-> (/ edge 2)
      double
      (#(Math/ceil %))
      int))


(defn edge-start [edge]
  (+ (sqr edge) 1))


(defn edge-end [edge]
  (sqr (inc edge)))


(defn get-edge-p1 [edge ring]
  (+ (edge-start edge) (dec ring)))


(defn get-edge-p2 [edge ring]
  (- (edge-end edge) ring))


(defn shortest-dist [n p1 p2]
  (min (Math/abs (- n p1))
       (Math/abs (- n p2))))


(defn ex1 [n]
  (let [edge (find-ring-edge n)
        ring (get-ring-for-edge edge)
        ; p1 & p2 are points in straight line from center
        p1 (get-edge-p1 edge ring)
        p2 (get-edge-p2 edge ring)
        d (shortest-dist n p1 p2)]
    (+ ring d)))


;; EX 2


;; when pos is
;;  x,  x turn left
;; -x, -x turn right
;; -x,  x turn down
;;  x, -x turn up
(defn next-dir [[[x y] d]]
  (cond
    (= x y) (if (pos? x) :l :r)
    (= (- x) y) (if (pos? y) :d d)
    (= (dec x) (- y)) (if (pos? x) :u d)
    :else d))


(defn go
  ([pos d1 d2]
   (-> (go pos d1)
       (go d2)))
  ([[x y] d]
   (case d
     :r [(inc x) y]
     :l [(dec x) y]
     :u [x (inc y)]
     :d [x (dec y)])))


(defn next-step [[pos d]]
  (let [new-pos (go pos d)
        new-dir (next-dir [new-pos d])]
    [new-pos new-dir]))


(defn get-cell [m pos]
  (get m pos 0))


(defn get-cells [m pos dirs]
  (map #(get-cell m (apply go (concat [pos] %))) dirs))


(defn get-neighbours [m [pos d]]
  (case d
    :r (get-cells m pos [[:r :u] [:u] [:u :l] [:l]])
    :l (get-cells m pos [[:l :d] [:d] [:d :r] [:r]])
    :u (get-cells m pos [[:u :l] [:l] [:l :d] [:d]])
    :d (get-cells m pos [[:d :r] [:r] [:r :u] [:u]])))


(defn get-value [m curr]
  (reduce + 0 (get-neighbours m curr)))


(defn ex2 [n]
  (loop [m {[0 0] 1}
         curr [[1 0] :u]]
    (let [[pos d] curr
          new-val (get-value m curr)]
      (if (> new-val n)
        new-val
        (recur (assoc m pos new-val) (next-step curr))))))
