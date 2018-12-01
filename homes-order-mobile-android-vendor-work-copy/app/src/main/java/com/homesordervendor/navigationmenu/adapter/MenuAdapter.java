package com.homesordervendor.navigationmenu.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.DeliverySlotItemBinding;
import com.homesordervendor.databinding.MenuItemBinding;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.navigationmenu.OnNavigationMenuListener;
import com.homesordervendor.navigationmenu.model.Menu;
import com.homesordervendor.navigationmenu.viewmodel.MenuItemVM;
import com.homesordervendor.user.deliveryslot.adapter.DeliverySlotAdapter;
import com.homesordervendor.user.deliveryslot.model.Slots;
import com.homesordervendor.user.deliveryslot.viewmodel.DeliverySlotItemVM;

import java.util.List;

/**
 * Created by mac on 3/1/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    Activity activity;
    List<Menu> menus;
    OnNavigationMenuListener listener;


    public MenuAdapter(Activity activity,List<Menu> menus,OnNavigationMenuListener listener){
        this.activity=activity;
        this.menus=menus;
        this.listener=listener;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MenuItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.menu_item, parent, false);
        return new MenuAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        holder.bind(menus.get(position));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private MenuItemBinding binding;
        public ViewHolder(MenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Menu menu) {
            binding.setMenuItemVM(new MenuItemVM(activity,listener));
            binding.setMenu(menu);
            binding.executePendingBindings();
        }
    }
}
