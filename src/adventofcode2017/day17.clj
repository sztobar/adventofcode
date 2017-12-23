(ns adventofcode2017.day17)


(defn spinlock-step [{:keys [steps coll pos]} i]
  (let [len (count coll)
        new-pos (inc (mod (+ pos steps) len))]
    {:steps steps
     :pos new-pos
     :coll (vec (concat (subvec coll 0 new-pos)
                        [(inc i)]
                        (subvec coll new-pos len)))}))


(defn start-spinlock
  ([steps] (start-spinlock steps 2017))
  ([steps times]
   (reduce spinlock-step
           {:steps steps
            :coll [0]
            :pos 0}
           (range times))))


(defn get-next-value [{:keys [coll pos]}]
  (coll (inc pos)))


(defn ex1
  ([] (ex1 367))
  ([input]
   (-> (start-spinlock input)
       get-next-value)))
;; 1487


;; PART TWO


(defn start-angry-spinlock [steps]
  (loop [len 1
         result nil
         pos 0]
    (if (> len 50000000)
      result
      (let [new-pos (inc (mod (+ pos steps) len))]
        (recur (inc len)
               (if (= new-pos 1)
                 len
                 result)
               new-pos)))))


(defn get-value-after-zero [{:keys [coll]}]
  (coll 1))


(defn ex2
  ([] (ex2 367))
  ([input]
   (start-angry-spinlock input)))
;; 25674054
