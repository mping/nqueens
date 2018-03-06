# N-Queens + 3-in-line

Place N queens on an NxN chess board so that none of them attack each other (the classic n-queens problem).
Additionally, please make sure that no three queens are in a straight line at ANY angle, 
so queens on A1, C2 and E3, despite not attacking each other, form a straight line.

# Notes

- Queen position is encoded as a 2d array, with `[row][col] = 1` indicating a queen. We could use a different scheme, ie,
  `[0 ... col]` were the index is the row and the value is the column.
- There are lots of different solutions, including a closed form (at least for NQueens only). I applied the solution that
  uses backtracking.


# Running

     gradle run -PappArgs="[4]"

# Testing

    ./gradlew test jacocoFullReport
    open build/reports/jacoco/jacocoFullReport/html/index.html