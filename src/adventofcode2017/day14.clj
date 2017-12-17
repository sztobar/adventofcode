(ns adventofcode2017.day14
  (:require [adventofcode2017.day10
             :refer [ex2]
             :rename {ex2 knot-hash}]
            [clojure.string :as str]
            [clojure.set :as set]))


(defn prepare-rows [input]
  (map #(str input "-" %) (range 128)))


(defn binarify [hex]
  (->> (vec hex)
       (map str)
       (map #(Integer/parseInt % 16))
       (map #(Integer/toBinaryString %))
       (map #(format "%4s" %))
       (map #(str/replace % " " "0"))
       str/join))


(defn count-used [bin]
  (count (str/replace bin #"0" "")))


(defn ex1
  ([] (ex1 "jzgqcdpd"))
  ([input]
   (->> (prepare-rows input)
        (map knot-hash)
        (map binarify)
        (map count-used)
        (reduce +))))
;; 8074


;; PART TWO


(defn create-coords []
   (mapcat (fn [y]
          (map (fn [x]
                 [x y])
               (range 256)))
        (range 256)))


(defn get-neighbours [coll [x y]]
  {:u {:v (get-in coll [(dec y) x] "0") :pos [x (dec y)]}
   :d {:v (get-in coll [(inc y) x] "0") :pos [x (inc y)]}
   :l {:v (get-in coll [y (dec x)] "0") :pos [(dec x) y]}
   :r {:v (get-in coll [y (inc x)] "0") :pos [(inc x) y]}})


(defn replace-group [coll src group-id]
  (reduce (fn [coll [x y]]
            (assoc-in coll [y x] group-id))
          coll
          src))


(defn add-neighbour [{:keys [groups coll next-group] :as agg} {:keys [pos v]} group-id]
  (if (= v "1")
    {:groups (update groups group-id conj pos)
     :coll (assoc-in coll (reverse pos) group-id)
     :next-group next-group}
    agg))


(defn add-to-group [{:keys [groups coll next-group]} [x y] group-id]
  {:groups (update groups group-id conj [x y])
   :coll (assoc-in coll [y x] group-id)
   :next-group next-group})


(defn next-step [{:keys [groups coll next-group] :as agg} pos]
  (let [[x y] pos
        v (get-in coll [y x])
        group (groups v)
        neighbours (get-neighbours coll [x y])
        right (:r neighbours)
        down (:d neighbours)]
    (cond
      (and (keyword? v)
           (keyword? (:v right))
           (not= v (:v right))) (let [group-id (:v right)]; merge curr-group with right-group
                                  (-> {:groups (-> (dissoc groups v)
                                                   (assoc group-id (set/union (get groups v)
                                                                              (get groups group-id))))
                                       :coll (replace-group coll (get groups v) group-id)
                                       :next-group next-group}
                                      (add-neighbour down group-id)))
      (and (keyword? v)
           (keyword? (:v right))
           (= v (:v right))) (let [group-id v]; same curr-group and right-group
                               (add-neighbour agg down group-id))
      (and (not (keyword? v))
           (keyword? (:v right))) (let [group-id (:v right)]; apply right-group
                                    (-> (add-to-group agg pos group-id)
                                        (add-neighbour down group-id)))
      (keyword? v) (let [group-id v]
                     (-> (add-neighbour agg down group-id)
                         (add-neighbour right group-id)))
      :else (let [group-id (keyword (str next-group))]; new-group
              (-> {:groups (assoc groups group-id #{[x y]})
                   :coll (assoc-in coll [y x] group-id)
                   :next-group (inc next-group)}
                  (add-neighbour down group-id)
                  (add-neighbour right group-id))))))


(defn make-groups [input]
  (->> (create-coords)
       (filter (fn [[x y]] (= "1" (get-in input [y x]))))
       (reduce next-step
               {:groups {}
                :coll input
                :next-group 1})))


(defn ex2
  ([] (ex2 "jzgqcdpd"))
  ([input]
   (->> (prepare-rows input)
        (map knot-hash)
        (map binarify)
        (mapv #(str/split % #""))
        make-groups
        :groups
        keys
        count)))
;; 1212
