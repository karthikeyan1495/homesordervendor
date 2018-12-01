package com.homesordervendor.user.holidayplanner.activelist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActiveItemBinding;
import com.homesordervendor.user.holidayplanner.activelist.viewmodel.ActiveItemVM;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/8/18.
 */

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<HolidayPlanner> list;

    public ActiveAdapter(Activity activity, List<HolidayPlanner> list){
        this.activity=activity;
        this.list=list;
    }

    @Override
    public ActiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActiveItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.active_item, parent, false);
        return new ActiveAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ActiveAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
        setUpObserver(holder.activeItemVM);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof ActiveItemVM) {
            HolidayPlanner items=(HolidayPlanner)object;
            list.remove(items);
            notifyDataSetChanged();
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ActiveItemBinding binding;
        ActiveItemVM activeItemVM;
        public ViewHolder(ActiveItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HolidayPlanner holidayPlanner) {
            activeItemVM=new ActiveItemVM(activity);
            binding.setActiveItemVM(activeItemVM);
            binding.setHolidayPlanner(holidayPlanner);
            binding.executePendingBindings();
        }
    }
}
