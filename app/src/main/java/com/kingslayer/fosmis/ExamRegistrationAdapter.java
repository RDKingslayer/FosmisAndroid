package com.kingslayer.fosmis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import REST_Classes.ExamCourses;


public class ExamRegistrationAdapter extends RecyclerView.Adapter<ExamRegistrationAdapter.ExamRegistrationViewHolder> {

    private List<ExamCourses> examCourses;
    private Context ctx;


    public ExamRegistrationAdapter(List<ExamCourses> examCourses, Context ctx) {
        this.examCourses = examCourses;
        this.ctx = ctx;
    }

    public class ExamRegistrationViewHolder extends RecyclerView.ViewHolder{

        TextView course_code;
        TextView course_name;
        TextView course_type;
        TextView course_credits;
        TextView status;
        Button btnReg;
        TextView exam_type;
        TextView exam_repeat;

        public ExamRegistrationViewHolder(@NonNull View itemView) {
            super(itemView);
            course_code = itemView.findViewById(R.id.txt_exam_Code);
            course_name = itemView.findViewById(R.id.txt_exam_Name);
            course_type = itemView.findViewById(R.id.txt_exam_Type);
            course_credits = itemView.findViewById(R.id.txt_exam_Credits);
            status = itemView.findViewById(R.id.txt_exam_Status);
            btnReg = itemView.findViewById(R.id.btn_exam_Register);
            exam_type = itemView.findViewById(R.id.txt_exam_Type);
            exam_repeat = itemView.findViewById(R.id.txt_exam_repeat);
        }
    }

    @NonNull
    @Override
    public ExamRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exam_registration_item, viewGroup, false);
        ExamRegistrationAdapter.ExamRegistrationViewHolder erv = new ExamRegistrationAdapter.ExamRegistrationViewHolder(v);
        return erv;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamRegistrationViewHolder holder, int i) {

        /*holder.course_code.setText(examCourses.get(i).);
        holder.course_name.setText(examCourses.get(i).);
        holder.course_type.setText(examCourses.get(i).);
        holder.course_credits.setText(examCourses.get(i).);
        holder.status.setText(examCourses.get(i).);
        holder.exam_type.setText(examCourses.get(i).);
        holder.exam_repeat.setVisibility(examCourses.get(i).);*/

    }

    @Override
    public int getItemCount() {
        return examCourses.size();
    }


}
