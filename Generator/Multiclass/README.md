#  BERT-based multi-class classifier

## Getting Started
### Custom task
 * In "glue_task_custom.py", add your own processer (similar to "ConceptnormProcessor" line 339) ,
 "glue_tasks_num_labels" (line 608), "glue_processors" (line 622), "glue_output_modes" (line 636)

### Data format
 * Please see data/train.tsv for the input format: each row is a pair of mention and concept.
 * data/label.txt is a json file that contains all the concepts from the ontology. 
 * To train the model, please put "train.tsv", "dev.tsv", "test.tsv", and "label.txt" into your "./path/to/data" folder  
 
### Different pre-trained models
 * [Pre-trained LM](https://huggingface.co/models?filter=pytorch)
 * [Pre-trained LM fine-tuned on text classification task](https://huggingface.co/models?filter=pytorch,text-classification)
 * If using model from local, in addition to the arguement "model_name_or_path",
 please also add arguements "--config_name", "--tokenizer_name".
 
 ### Code to run
* code to run multi-class classifier for the concept normalization tasks using askapatient dataset:
```
python3.7 run_glue_custom.py \
--model_name_or_path bert-base-uncased \
--task_name conceptnorm \
--do_train \
--do_eval \
--do_predict \
--data_dir data/askapatient/0/ \
--label_dir data/askapatient/label.txt \
--max_seq_length 64 \
--per_device_eval_batch_size=8 \
--per_device_train_batch_size=8 \
--learning_rate 2e-5 \
--num_train_epochs 3.0 \
--output_dir /path/to/model_new/```