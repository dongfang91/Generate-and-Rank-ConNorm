#  BERT-based multi-class classifier

## Getting Started
 
### Data format
 * Please see data/train.tsv for the input format: each row is a pair of mention and concept.
 * data/label.txt is a json file that contains all the concepts from the ontology. 
 * To train the model, please put "train.tsv", "dev.tsv", "test.tsv", and "label.txt" into your "./path/to/data" folder  
 
 ### Code to run
* code to run multi-class classifier:
```
singularity exec --nv /extra/dongfangxu9/image_bert/hpc-ml_centos7-python37.sif python3.7 run_glue_custom.py \
--task_name twa \
--do_train \
--do_eval \
--data_dir ./path/to/data/ \
--label_dir ./path/to/data/label.txt \
--model_name_or_path /path/to/biobert \
--config_name /path/to/biobert/bert_config.json \
--model_type bert \
--max_seq_length 32 \
--per_gpu_train_batch_size 16 \
--save_steps 301 \
--warmup_steps 903 \
--eval_all_checkpoints \
--learning_rate 5e-5 \
--num_train_epochs 30 \
--output_dir /path/to/model_new/```