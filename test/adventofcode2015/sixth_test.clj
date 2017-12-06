(ns adventofcode2015.sixth-test
  (:require [clojure.test :refer :all]
            [adventofcode2015.sixth :refer :all]))

(deftest a-test
  (testing "total brithness"
    (is (= (get-brigthness! "turn on 0,0 through 0,0") 1))
    (is (= (get-brigthness! "toggle 0,0 through 999,999") 2000000))
    #_(is (= (get-brigthness! "turn on toggle 0,0 through 999,999") 1000000))))

