(ns adventofcode2017.day9-test
  (:require [clojure.test :refer :all]
            [adventofcode2017.day9 :as d9]))

(deftest ex1-test

  (testing "group score"

    (is (= (d9/get-score "{}")
           1))

    (is (= (d9/get-score "{{{}}}")
           6))

    (is (= (d9/get-score "{{},{}}")
           5))

    (is (= (d9/get-score "{{{}m{},{{}}}}")
           16))

    (is (= (d9/get-score "{<a>,<a>,<a>,<a>}")
           1))

    (is (= (d9/get-score "{{<ab>},{<ab>},{<ab>},{<ab>}}")
           9))

    (is (= (d9/get-score "{{<!!>},{<!!>},{<!!>},{<!!>}}")
           9))

    (is (= (d9/get-score "{{<a!>},{<a!>},{<a!>},{<ab>}}")
           3))))

