(ns adventofcode2017.day7
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))


(defn get-input
  ([]
   (get-input "2017/day7.data"))
  ([filename]
   (->> (io/resource filename)
        io/reader
        line-seq)))


(defn parse-weight [w]
  (Integer/parseInt
    (str/replace w #"[\(\)]" "")))


(defn parse-children [line]
  (if (some? line)
    (str/split line #",\s")
    nil))


(defn parse-input [input]
  (reduce
   (fn [coll line]
     (let [[node childs] (str/split line #"\s->\s")
           [name weight] (str/split node #"\s")]
       (assoc coll name {:name name
                         :weight (parse-weight weight)
                         :children (parse-children childs)})))
     {}
     input))


(defn create-tree [coll]
  (reduce
   (fn [coll [parent-k node]]
     (reduce (fn [coll child-k]
               (update coll child-k assoc :parent parent-k))
             coll
             (:children node)))
   coll
   (filter #(-> % second :children count) coll)))


(defn get-root [coll]
  (first
   (filter
    (fn [v] (nil? (:parent v)))
    (vals coll))))


(defn get-root-name [input]
  (-> (parse-input input)
      create-tree
      get-root
      :name))


(defn ex1 []
  (get-root-name (get-input)))


;; PART TWO


(defn same-weights? [coll]
  (not=
   false
   (reduce
    (fn [agg v]
      (if (= agg v)
        agg
        (reduced false)))
    (map :weight-sum coll))))


(defn sum-weights [coll {:keys [name children weight] :as node}]
  (if (some? children)
    (let [child-nodes (map coll children)
          new-coll (reduce
                    (fn [coll node]
                      (let [[new-coll node-weight-sum] (sum-weights coll node)
                            weight (get-in new-coll [name :weight-sum])]
                        (assoc-in new-coll [name :weight-sum] (+ weight node-weight-sum))))
                    (assoc-in coll [name :weight-sum] weight)
                    child-nodes)
          child-nodes (map new-coll children)
          new-coll (assoc-in new-coll [name :balanced?] (same-weights? child-nodes))]
      [new-coll (get-in new-coll [name :weight-sum])])
    [(assoc-in coll [name :weight-sum] weight) weight]))


(defn get-topmost-unbalanced [coll root]
  (loop [node root]
    (let [child-nodes (map coll (:children node))
          unbalanced-node (first (filter #(not (:balanced? %)) child-nodes))]
      (if (some? unbalanced-node)
        (recur unbalanced-node)
        node))))


(defn get-children [coll node]
  (map coll (:children node)))


(defn separate-weights [nodes]
  (let [grouped (group-by :weight-sum nodes)
        [bad] (first (filter (fn [v] (= (count v) 1)) (vals grouped)))
        good (first (filter #(not= (:name %) (:name bad)) nodes))]
    [good bad]))


(defn ex2
  ([]
   (ex2 "2017/day7.data"))
  ([filename]
   (let [coll (-> (get-input filename)
                  parse-input
                  create-tree)
         root (get-root coll)
         [coll] (sum-weights coll root)
         root (get-root coll)
         unbalanced (get-topmost-unbalanced coll root)
         child-nodes (get-children coll unbalanced)
         [good bad] (separate-weights child-nodes)]
     (+ (:weight bad)
        (- (:weight-sum good)
           (:weight-sum bad))))))
