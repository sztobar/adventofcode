(ns adventofcode2015.second-test
  (:require [clojure.test :refer :all]
            [adventofcode2015.second :refer :all]))

(deftest a-test
  (testing "A present with dimensions '2x3x4'"
    (is (= (need-paper "2x3x4") 58))
    (is (= (need-paper "4x3x2") 58))
    (is (= (need-paper "4x2x3") 58))
    (is (= (need-paper "3x2x4") 58))
    (is (= (need-paper "3x4x2") 58)))

  (testing "A present with dimensions '1x1x10'"
    (is (= (need-paper "1x1x10") 43))
    (is (= (need-paper "1x10x1") 43))
    (is (= (need-paper "10x1x1") 43))))
