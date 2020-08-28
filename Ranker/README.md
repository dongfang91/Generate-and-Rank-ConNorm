# BERT-based listwise classifier
 After the generator generates lists of candidate concepts, BERT-based 
 listwise classifier will take the candidate concepts and the mentions
 as input and make a predictions.
 
 ## Getting Started
 
 ### Data format
 * The format of the input data is shown in "./data/biobert_umls_n2c2_new30_train_oracle_sampled/train.tsv"
 * Basically, each row is one mention, the synonyms of its candidates, the semantic labels of each candidate, and the index of gold truth concept
  ### Code to run
 * * code to run listwise classifier for the concept normalization tasks using n2c2 samples:
```
python3.7 run_multiple_choice_custom.py \
--task_name n2c2 \
--model_name_or_path bert-base-uncased \
--do_train \
--do_eval
--data_dir data/biobert_umls_n2c2_new30_train_oracle_sampled
--n_labels 30 \
--learning_rate 5e-5 \
--num_train_epochs 3 \
--max_seq_length 32 \
--output_dir output/n2c2_base \
--per_device_train_batch_size 2 \
--gradient_accumulation_steps 2 \
--lambda_1 0.0 \
--lambda_2 0.1 \
--margin1 0.1 \
--margin2 0.1 \
--save_steps 4 \
--evaluate_during_training \
--eval_steps 4
```