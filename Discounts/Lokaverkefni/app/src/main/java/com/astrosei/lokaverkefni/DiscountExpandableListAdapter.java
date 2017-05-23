package com.astrosei.lokaverkefni;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.astrosei.lokaverkefni.persistence.entities.Discount;

import java.util.List;

/**
 * Created by astrosei on 31/03/2017.
 * Used to create the ExpandableListView for DiscountActivity
 */

public class DiscountExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Discount> discounts;

    public DiscountExpandableListAdapter(
            Context context, List<Discount> discounts){
        this.context = context;
        this.discounts = discounts;
    }

    @Override
    public int getGroupCount() {
        return discounts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return discounts.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return discounts.get(groupPosition).getDiscountInfo();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final String title = (String)getGroup(groupPosition);
        final String companyName = getCompanyName(groupPosition);
        final String category = discounts.get(groupPosition).getCategory();
        final String discountProvider = discounts.get(groupPosition).getDiscountProvider();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.discount_list_title_layout, null);
        }

        // Set discount title.
        TextView listTitle = (TextView)convertView.findViewById(R.id.textView_title);
        listTitle.setTypeface(null, Typeface.BOLD);
        listTitle.setText(title);

        // Set the company logo as image for disount.
        // If no logo is available then the image is the category icon.
        ImageView companyLogo = (ImageView)convertView.findViewById(R.id.imageView_companyLogo);
        int companyId = context.getResources().getIdentifier("drawable/"+ companyName,null,context.getPackageName());
        if(companyId!=0){
            companyLogo.setImageResource(companyId);
        }
        else if(category.equals(context.getResources().getString(R.string.food))){
            companyLogo.setImageResource(R.drawable.food);
        }
        else if(category.equals(context.getResources().getString(R.string.clothes))){
            companyLogo.setImageResource(R.drawable.clothes);
        }
        else if(category.equals(context.getResources().getString(R.string.entertainment))){
            companyLogo.setImageResource(R.drawable.entertainment);
        }
        else if(category.equals(context.getResources().getString(R.string.lifestyle))){
            companyLogo.setImageResource(R.drawable.lifestyle);
        }
        else if(category.equals(context.getResources().getString(R.string.technology))){
            companyLogo.setImageResource(R.drawable.technology);
        }
        else if(category.equals(context.getResources().getString(R.string.other))){
            companyLogo.setImageResource(R.drawable.other);
        }

        // Set discount provider.
        ImageView providerLogo = (ImageView)convertView.findViewById(R.id.imageView_discountProvider);
        int providerId = context.getResources().getIdentifier("drawable/"+discountProvider,null,context.getPackageName());
        providerLogo.setImageResource(providerId);

        // Put a red flagin header if the discount has a condition.
        ImageView condition = (ImageView)convertView.findViewById(R.id.imageView_condition);
        if(discounts.get(groupPosition).getIsCondition()){
            condition.setImageResource(R.drawable.condition);
            condition.setVisibility(View.VISIBLE);
        }
        else{
            condition.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String info = (String)getChild(groupPosition,childPosition);
        final String condition = discounts.get(groupPosition).getCondition();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.discount_list_item_layout, null);
        }
        TextView listInfo = (TextView)convertView.findViewById(R.id.textView_info);
        listInfo.setText(info);

        // Show condition if there is one.
        TextView conditionText = (TextView)convertView.findViewById(R.id.textView_condition);
        ImageView conditionImage = (ImageView)convertView.findViewById(R.id.imageView_condition);

        if(discounts.get(groupPosition).getIsCondition()){
            conditionText.setText(condition);
            conditionImage.setImageResource(R.drawable.condition);
            conditionText.setVisibility(View.VISIBLE);
            conditionImage.setVisibility(View.VISIBLE);
        }
        else{
            conditionText.setVisibility(View.GONE);
            conditionImage.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    // Added function for clarity.
    public String getCompanyName(int groupPosition){
        return discounts.get(groupPosition).getCompanyName();
    }
}
