package com.payfacesdkusage;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import br.com.payface.hybrid.HybridFrameFragment;

public class PayfaceSDKModuleManager extends ViewGroupManager<FrameLayout> {
    public static final String NAME_CLASS = "PayfaceSDKModuleManager";
    ReactApplicationContext mCallerContext;
    private String partner = "";
    private String name= "";
    private String cpf= "";
    private String cellphone= "";
    private HybridFrameFragment.Environment environment = HybridFrameFragment.Environment.SANDBOX;
    public final int COMMAND_CREATE = 1;

    public PayfaceSDKModuleManager(ReactApplicationContext reactContext) {
       this.mCallerContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "RCTPayfaceSDKFragment";
    }

    @NonNull
    @Override
    protected FrameLayout createViewInstance(@NonNull ThemedReactContext context) {
        Log.d(NAME_CLASS, "PreferenceView createViewInstance");
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_hybrid, null);
        return frameLayout;
    }

    @ReactProp(name="partner")
    public void setPartner(FrameLayout videoView, String partner) {
        Log.d(NAME_CLASS, "set partner " + partner);
        this.partner = partner;
    }

    @ReactProp(name="nameClient")
    public void setNameClient(FrameLayout videoView, String name) {
        Log.d(NAME_CLASS, "set name " + name);
        this.name = name;
    }

    @ReactProp(name="cellphone")
    public void setCellphone(FrameLayout videoView, String cellphone) {
        Log.d(NAME_CLASS, "set cellphone " + cellphone);
        this.cellphone = cellphone;
    }

    @ReactProp(name="cpf")
    public void setCPF(FrameLayout videoView, String cpf) {
        Log.d(NAME_CLASS, "set cpf " + cpf);
        this.cpf = cpf;
    }

    @ReactProp(name="environment")
    public void setEnvironment(FrameLayout videoView, String environment) {
        Log.d(NAME_CLASS, "set environment " + environment + HybridFrameFragment.Environment.PRODUCTION);
        if (environment.equals(HybridFrameFragment.Environment.PRODUCTION.toString().toLowerCase())) {
            this.environment = HybridFrameFragment.Environment.PRODUCTION;
        }
        else {
            this.environment = HybridFrameFragment.Environment.SANDBOX;
        }
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("create", COMMAND_CREATE);
    }

    @Override
    public void receiveCommand(@NonNull FrameLayout root, String commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);

        int commandIdInt = Integer.parseInt(commandId);
        Log.d(NAME_CLASS, "Command " + commandIdInt);

        if (commandIdInt == COMMAND_CREATE) {
            createFragment(root);
        }
    }

    public void createFragment(FrameLayout parentLayout) {
        Log.d(NAME_CLASS, "createFragment");
        setupLayoutHack(parentLayout);
        final HybridFrameFragment myFragment = new HybridFrameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("partner", partner);
        bundle.putString("name", name);
        bundle.putString("cpf", cpf);
        bundle.putString("cellphone", cellphone);
        bundle.putSerializable("environment", environment);
        myFragment.setArguments(bundle);

        FragmentManager fragmentManager = ((FragmentActivity) this.mCallerContext.getCurrentActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(parentLayout.getId(), myFragment).commit();

    }

    void setupLayoutHack(ViewGroup view) {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long l) {
                manuallyLayoutChildren(view);
                view.getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    void manuallyLayoutChildren(ViewGroup view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);

            child.measure(
                    View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(),
                                                     View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(),
                                                     View.MeasureSpec.EXACTLY));

            child.layout(0,0,child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }
}
