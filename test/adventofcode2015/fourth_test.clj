(ns adventofcode2015.fourth-test
  (:require [clojure.test :refer :all]
            [adventofcode2015.fourth :refer :all]))

(deftest a-test
  (testing "Mining AdventsCoins"
    (is (= (get-advent-coin "abcdef") 609043))
    (is (= (get-advent-coin "pqrstuv") 1048970))))
