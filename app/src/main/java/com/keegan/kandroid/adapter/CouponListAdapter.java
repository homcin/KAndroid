package com.keegan.kandroid.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keegan.kandroid.R;
import com.keegan.kandroid.util.CouponPriceUtil;
import com.keegan.model.CouponEntity;


/**
 * Created by Administrator on 2016/7/9.
 */
public class CouponListAdapter extends KBaseAdapter<CouponEntity> {

    public CouponListAdapter (Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_coupon_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            viewHolder.tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        CouponEntity coupon = (CouponEntity) getItem(position);
        viewHolder.tvTitle.setText(coupon.getName());
        viewHolder.tvInfo.setText(coupon.getIntroduce());
        SpannableString priceString;
        // 根据不同的券类型展示不同的价格显示方式
        switch (coupon.getModelType()) {
            default:
            case CouponEntity.TYPE_CASH:
                priceString = CouponPriceUtil.getCashPrice(context, coupon.getFaceValue(), coupon.getEstimateAmount());
                break;
            case CouponEntity.TYPE_DEBIT:
                priceString = CouponPriceUtil.getVoucherPrice(context, coupon.getDebitAmount(), coupon.getMiniAmount());
                break;
            case CouponEntity.TYPE_DISCOUNT:
                priceString = CouponPriceUtil.getDiscountPrice(context, coupon.getDiscount(), coupon.getMiniAmount());
                break;
        }
        viewHolder.tvPrice.setText(priceString);

        return view;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvInfo;
        TextView tvPrice;
    }
}
