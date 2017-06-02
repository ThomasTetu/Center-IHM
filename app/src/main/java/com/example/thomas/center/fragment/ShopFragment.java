package com.example.thomas.center.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thomas.center.R;
import com.example.thomas.center.model.ShopType;

/**
 * Classe fragment contenant une viewpager permettant de naviguer et de visualiser les différentes listes de boutiques/évènements
 * classés par type.
 * Created by thomas on 14/05/17.
 */
public class ShopFragment extends Fragment {

    private ViewPager viewPager;


    /**
     * Méthode permettant d'instancier la vue contenue dans le fragment.
     * @param inflater : utilisé pour instancier la vue avec le layout (XML) voulu.
     * @return : la vue principal du fragment.
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_fragment, container, false);
    }

    /***
     * Méthode permettant de lire les données du modeles et de les charger dans la vue.
     * Ici les onglets de chaque type de boutiques/évènements ainsi que le viewPager.
     * @param view : affichant les objets models.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        //setting Tab layout (number of Tabs = number of ViewPager pages)
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        for (ShopType s : ShopType.values()) {
            tabLayout.addTab(tabLayout.newTab().setText(s.getTypeString()));
        }

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Inner classe permettant la gestion du viewPager
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Retourne un fragment ShopTypeFragment selon le type sélectionné
         * @param position : position de l'onglet sélectionné correspondant à un type de boutiques/ évènements
         * @return ShopTypeFragment
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShopTypeFragment(ShopType.MODE);
                case 1:
                    return new ShopTypeFragment(ShopType.DECO);
                default:
                    return new ShopTypeFragment(ShopType.SPORT);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
