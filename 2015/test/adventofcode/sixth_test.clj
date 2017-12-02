(ns adventofcode.sixth-test
  (:require [clojure.test :refer :all]
            [adventofcode.sixth :refer :all]))

(deftest a-test
  (testing "total brithness"
    (is (= (get-brigthness! "turn on 0,0 through 0,0") 1))
    (is (= (get-brigthness! "toggle 0,0 through 999,999") 2000000))
    (is (= (get-brigthness! "turn on toggle 0,0 through 999,999") 1000000))))

