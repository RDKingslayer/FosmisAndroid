package com.kingslayer.fosmis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import REST_Classes.CoreCourse;

public class OptionalCourseAdapter extends RecyclerView.Adapter<OptionalCourseAdapter.OptionalCourseViewHolder> {

    private List<CoreCourse> optionalCourses;
    private Context ctx;
    private int status = -1;

    public OptionalCourseAdapter(List<CoreCourse> optionalCourses, Context ctx) {
        this.optionalCourses = optionalCourses;
        this.ctx = ctx;
    }

    public class OptionalCourseViewHolder extends RecyclerView.ViewHolder{

        TextView course_code;
        TextView course_name;
        TextView course_type;
        TextView course_credits;
        TextView status;
        Button btnReg;
        RadioGroup radioBtnGrp;
        RadioButton rdeg;
        RadioButton rnondeg;

        public OptionalCourseViewHolder(View itemView)
        {
            super(itemView);

            course_code = itemView.findViewById(R.id.txtCode);
            course_name = itemView.findViewById(R.id.txtName);
            course_type = itemView.findViewById(R.id.txtType);
            course_credits = itemView.findViewById(R.id.txtCredits);
            status = itemView.findViewById(R.id.txtStatus);
            btnReg = itemView.findViewById(R.id.btnRegister);
            radioBtnGrp = itemView.findViewById(R.id.radioDegree);
            rdeg  = itemView.findViewById(R.id.rdeg);
            rnondeg = itemView.findViewById(R.id.rnondeg);
        }
    }

    @NonNull
    @Override
    public OptionalCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.optional_course_row_item, viewGroup, false);
        OptionalCourseAdapter.OptionalCourseViewHolder pvh = new OptionalCourseAdapter.OptionalCourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final OptionalCourseViewHolder holder, int i) {

        holder.course_code.setText(optionalCourses.get(i).getCode());
        holder.course_name.setText(optionalCourses.get(i).getTitle());
        holder.course_type.setText(optionalCourses.get(i).getType());
        holder.course_credits.setText(optionalCourses.get(i).getCredits());
        holder.status.setText(optionalCourses.get(i).getRegStat());

        if(optionalCourses.get(i).getDegreeStat().equals("NON Degree"))
        {
            holder.rnondeg.setChecked(true);
        }
        holder.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.radioBtnGrp.getCheckedRadioButtonId() == R.id.rdeg)
                {
                    status = 1;
                }
                else if(holder.radioBtnGrp.getCheckedRadioButtonId() == R.id.rnondeg)
                {
                    status = 0;
                }
                RegisterCourse(holder.course_code.getText().toString(), ctx, status);
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionalCourses.size();
    }

    public void RegisterCourse(String course, Context ctx, int stat)
    {
        OptionalCourseRegistration.setCourse(course,ctx, stat);
    }
}
