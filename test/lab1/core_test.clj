(ns lab1.core-test
  (:require [clojure.test :refer :all]
            [lab1.core :refer :all]))

(deftest distance_test
  (testing "Distance function"
    (is (= (distance '(0, 0) '(1, 0)) 1))
    (is (= (distance '(0, 0) '(3, 4)) 5))
    (is (= (distance '(4, 4) '(4, 4)) 0))
    (is (= (distance '(0, 0) '(5, 0)) (distance '(5, 0) '(0, 0))))))
