package com.kingslayer.fosmis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import REST_Classes.CoreCourse;

public class CoreCourseAdapter extends RecyclerView.Adapter<CoreCourseAdapter.CoreCourseViewHolder>{

    private List<CoreCourse> coreCourses;

    public CoreCourseAdapter(List<CoreCourse> coreCourses) {
        this.coreCourses = coreCourses;
    }

    public class CoreCourseViewHolder extends RecyclerView.ViewHolder {

        TextView course_code;
        TextView course_name;
        TextView course_type;
        TextView course_credits;
        TextView status;

        public CoreCourseViewHolder(@NonNull View itemView) {
            super(itemView);

            course_code = itemView.findViewById(R.id.txtCode);
            course_name = itemView.findViewById(R.id.txtName);
            course_type = itemView.findViewById(R.id.txtType);
            course_credits = itemView.findViewById(R.id.txtCredits);
            status = itemView.findViewById(R.id.txtStatus);
        }
    }

    @NonNull
    @Override
    public CoreCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_row_item, viewGroup, false);
        CoreCourseViewHolder pvh = new CoreCourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoreCourseViewHolder holder, int i) {

        holder.course_code.setText(coreCourses.get(i).getCode());
        holder.course_name.setText(coreCourses.get(i).getTitle());
        holder.course_type.setText(coreCourses.get(i).getType());
        holder.course_credits.setText(coreCourses.get(i).getCredits());
        holder.status.setText(coreCourses.get(i).getRegStat());
    }

    @Override
    public int getItemCount() {
        return coreCourses.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
