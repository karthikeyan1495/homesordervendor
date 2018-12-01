package com.homesordervendor.user.shippingarea.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.AreaItemBinding;
import com.homesordervendor.user.shippingarea.model.Area;
import com.homesordervendor.user.shippingarea.model.State;
import com.homesordervendor.user.shippingarea.viewmodel.AreaItemVM;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 2/27/18.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<Area> areas;
    State state;
    boolean isAreaEditable;

    OnAreaListener onAreaListener;

    public AreaAdapter(Activity activity,List<Area> areas,State state,boolean isAreaEditable){
        this.activity=activity;
        this.areas=areas;
        this.state=state;
        this.isAreaEditable=isAreaEditable;
    }

    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AreaItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.area_item, parent, false);
        return new AreaAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AreaAdapter.ViewHolder holder, int position) {
        holder.bind(areas.get(position),state);
        setUpObserver(holder.areaItemVM);
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof AreaItemVM) {
            if (onAreaListener!=null){
                onAreaListener.onAreaSelected();
            }
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public void setOnAreaListener(OnAreaListener onAreaListener){
        this.onAreaListener=onAreaListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private AreaItemBinding binding;
        public AreaItemVM areaItemVM;
        public ViewHolder(AreaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Area area, State state) {
            areaItemVM=new AreaItemVM(activity);
            binding.setAreaItemVM(areaItemVM);
            binding.setArea(area);
            binding.setState(state);
            binding.setIsAreaEditable(isAreaEditable);
            binding.executePendingBindings();
        }
    }

    public interface OnAreaListener{
        void onAreaSelected();
    }
}
