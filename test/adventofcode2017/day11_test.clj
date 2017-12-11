(ns adventofcode2017.day11-test
  (:require [adventofcode2017.day11 :as d11]
            [clojure.test :refer :all]))

(deftest ex1-test

  (testing "calculating distance"

    (is (= (d11/ex1 "ne,ne,ne")
           3))

    (is (= (d11/ex1 "ne,ne,sw,sw")
           0))

    (is (= (d11/ex1 "ne,ne,s,s")
           2))

    (is (= (d11/ex1 "se,sw,se,sw,sw")
           3))))

