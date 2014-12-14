# lab1

Chiu fuzzy cluster estimation

## Usage

`lein run <filename> <ra>`

`<filename>` - path to a file with input data. Each row represents an array of object attributes.

`<ra>` - radius defining a neighborhood.

## Output

List of cluster core's indexes in input file (starts with 0)

## Examples

`lein run DataFiles/flowers.txt 1.5`

`lein run DataFiles/butterfly.txt 3.0`

`lein run DataFiles/glass.txt 1.2`
