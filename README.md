# Generate-and-Rank-ConNorm
 Generate-and-Rank Framework with Semantic Type Regularization for Biomedical Concept Normalization

## Setup
* We recommend Python 3.6 or higher. The code does **not** work with Python 2.7.
* BERT-based multi-class classifier and BERT-based listwise classifier
  are implemented with PyTorch (at least 1.3.0) using [transformers v2.1.0](https://github.com/huggingface/transformers).
* Lucene-based dictionary look-up system is developed using the [lucene-8.1.1](https://lucene.apache.org/).

## Getting Started

### Candidate Generaotr
* [BERT-based multi-class classifier](https://github.com/dongfang91/Generate-and-Rank-ConNorm/tree/master/Generator/Multiclass): Generator/Multiclass/
* [Lucene-based dictionary look-up system](https://github.com/dongfang91/Generate-and-Rank-ConNorm/tree/master/Generator/Lucene): Generator/Lucene/

### Candidate ranker
* [BERT-based listwise classifier](https://github.com/dongfang91/Generate-and-Rank-ConNorm/tree/master/Ranker): Ranker/