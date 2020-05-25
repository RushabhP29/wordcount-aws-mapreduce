package com.wordcount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountMapper extends Mapper<Object, Text, Text, IntWritable> {
	
	private HashMap<String, IntWritable> hashmap;

	// setup method for version 3
	@Override
	protected void setup(Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {

		hashmap = new HashMap<String, IntWritable>();


			// TODO Auto-generated method stub
		super.setup(context);
	}//end of setup method

	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException {

		
		//version - 3
		String[] words = value.toString().split(" ");
		IntWritable countWritable = new IntWritable(1);
		for (String string : words) {
			if (string.length() > 0) {
				if (hashmap.containsKey(string)) {
					int count = hashmap.get(string).get();
					count++;
					// countWritable.set(count);
					hashmap.put(string, new IntWritable(count));
				} else {
					hashmap.put(string, countWritable);

				}
			}
		}

	}// end of map method 

	@Override
	protected void cleanup(Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		for (Map.Entry<String, IntWritable> entry : hashmap.entrySet()) {
			String mystr = entry.getKey();
			IntWritable myint = entry.getValue();
			context.write(new Text(mystr), myint);
		}
		super.cleanup(context);
	}//end of cleanup method

}// end of mapper class