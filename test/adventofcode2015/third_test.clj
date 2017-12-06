(ns adventofcode2015.third-test
  (:require [clojure.test :refer :all]
            [adventofcode2015.third :refer :all]))

(deftest santa-test
  (testing "Visited houses on santa`s radar"
    (is (= (deliver-presents ">") 2))
    (is (= (deliver-presents "^>v<") 4))
    (is (= (deliver-presents "^v^v^v^v^v") 2))))

(deftest robo-test
  (testing "Santa with his robot helper"
    (is (= (robo-santa "^v") 3))
    (is (= (robo-santa "^>v<") 3))
    (is (= (robo-santa "^v^v^v^v^v") 11))))
