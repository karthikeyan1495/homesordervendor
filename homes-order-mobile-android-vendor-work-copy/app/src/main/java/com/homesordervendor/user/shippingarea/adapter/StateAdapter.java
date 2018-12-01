package com.homesordervendor.user.shippingarea.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.StateItemBinding;
import com.homesordervendor.user.shippingarea.model.State;
import com.homesordervendor.user.shippingarea.viewmodel.StateItemVM;

import java.util.List;

/**
 * Created by mac on 2/24/18.
 */

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    Activity activity;
    List<State> states;
    boolean isAreaEditable;


    AreaAdapter.OnAreaListener onAreaListener;

    public StateAdapter(Activity activity,List<State> states,boolean isAreaEditable){
        this.activity=activity;
        this.states=states;
        this.isAreaEditable=isAreaEditable;
    }

    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StateItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.state_item, parent, false);
        return new StateAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        holder.bind(states.get(position));
        setAreaView(holder,position);
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public void setOnAreaListener(AreaAdapter.OnAreaListener onAreaListener){
        this.onAreaListener=onAreaListener;
    }

    private void setAreaView(StateAdapter.ViewHolder holder,int position){
        AreaAdapter adapter=new AreaAdapter(activity,states.get(position).getAreas(),states.get(position),isAreaEditable);
        holder.binding.streetRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        holder.binding.streetRecyclerView.setAdapter(adapter);
        adapter.setOnAreaListener(onAreaListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private StateItemBinding binding;
        public ViewHolder(StateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(State state) {
           binding.setStateItemVM(new StateItemVM(activity));
           binding.setState(state);
           binding.executePendingBindings();
        }
    }


}
