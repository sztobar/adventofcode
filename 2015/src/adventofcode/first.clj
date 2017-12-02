(ns adventofcode.first)

(defn santa-step
  "ho ho ho"
  ([s i l]
   (let [c (get s i)]
     (cond
       (= c \() (inc l)
       (= c \)) (dec l)))))

(defn santa-go [s]
  (loop [i 0 l 0]
    (let [l' (santa-step s i l)
          c (get s i)]
      (if (nil? c) l (recur (inc i) l')))))


(defn santa-basement [s]
  (loop [i 0 l 0]
    (let [l (santa-step s i l)]
      (if (= l -1) i (recur (inc i) l)))))
