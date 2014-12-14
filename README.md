# lab1

Chiu fuzzy cluster estimation

## Installation

Download from http://example.com/FIXME.

## Usage

lein run <filename> <ra>

<filename> - path to a file with input data. Each row represents an array of object attributes. If the last attribute contains non-numeric characters, it is considered to be cluster name.
<ra> - radius defining a neighborhood.

## Examples

lein run resources/flowers.txt 1.5
