(ns adventofcode2017.day15)


(def A-factor 16807)
(def B-factor 48271)
(def div-val 2147483647)


(defn same-last-16-bits [A B]
  (= (take-last 16 (Integer/toBinaryString A))
     (take-last 16 (Integer/toBinaryString B))))


(defn next-val [val factor]
  (mod (* val factor) div-val))


(defn judge-next-value [{:keys [judge A B]} _]
  (let [new-A (next-val A A-factor)
        new-B (next-val B B-factor)]
    {:A new-A
     :B new-B
     :judge (if (same-last-16-bits new-A new-B)
              (inc judge)
              judge)}))

(defn ex1
  ([] (ex1 {:A 699 :B 124 :it 40000000}))
  ([{:keys [A B it]}]
   (:judge
    (reduce judge-next-value {:judge 0
                              :A A
                              :B B}
            (range it)))))
;; 600

;; PART TWO


(def A-multiply 4)
(def B-multiply 8)


(defn next-until-multiply-of [start-val factor multiply]
  (loop [val (next-val start-val factor)]
    (if (= 0 (mod val multiply))
      val
      (recur (next-val val factor)))))


(defn judge-next-picky-value [{:keys [judge A B]} _]
  (let [new-A (next-until-multiply-of A A-factor A-multiply)
        new-B (next-until-multiply-of B B-factor B-multiply)]
    {:A new-A
     :B new-B
     :judge (if (same-last-16-bits new-A new-B)
              (inc judge)
              judge)}))



(defn ex2
  ([] (ex2 {:A 699 :B 124 :it 5000000}))
  ([{:keys [A B it]}]
   (:judge
    (reduce judge-next-picky-value {:judge 0
                                    :A A
                                    :B B}
            (range it)))))
