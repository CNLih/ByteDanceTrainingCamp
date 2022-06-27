package com.bytedance.application.yuekangcode;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bytedance.application.R;
import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.databinding.ActivityNucleicCodeBinding;
import com.bytedance.application.model.AppModel;
import com.bytedance.application.model.entity.CodeEntity;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class NucleicCodeActivity extends BaseInitActivity<ActivityNucleicCodeBinding> {
    private AlertDialog.Builder hintDialogBuilder;
    private AlertDialog hintDialog;
    private final ActivityResultLauncher<String> getPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), access -> {
                if(!access){
                    hintDialog.show();
                }
            });
    private String imageUri;
    private EditText editText;
    private AlertDialog.Builder titleBuilder;
    private AlertDialog titleDialog;
    private ViewPagerAdapter viewAdapter;

    //Activity result API
    //简单使用 https://juejin.cn/post/7082314521284444173
    private final ActivityResultLauncher<String> getImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(), result -> {
                if(result == null){
                    return;
                }
                //获取图片uri后保存图片uri以及获取命名
                imageUri = result.toString();
                titleDialog.show();
            }
    );

    @Override
    protected ActivityNucleicCodeBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_nucleic_code);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nucleic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                getPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                getImage.launch("image/*");
                break;
            case R.id.action_remove:
                int removeI = binding.viewpager.getCurrentItem();
                if(AppModel.getInstance().loadCodeList().size() == 0){
                    break;
                }
                AppModel.getInstance().removeCode(removeI);
                viewAdapter.notifyRemoveItem(AppModel.getInstance().loadCodeList(), removeI);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("核酸码");
        editText = new EditText(this);
        titleBuilder = new AlertDialog.Builder(this)
                .setTitle("请输入类型：")
                .setView(editText)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("确认", ((dialogInterface, i) -> {
                    Editable text;
                    if((text = editText.getText()) != null){
                        int changedIndex = AppModel.getInstance().saveCode(text.toString(), imageUri);
                        //动态更新viewpager集合
                        viewAdapter.notifyItem(AppModel.getInstance().loadCodeList(), changedIndex);
                        dialogInterface.dismiss();
                    }else {
                        Toast.makeText(editText.getContext(), "请正确输入核酸码类型", Toast.LENGTH_SHORT).show();
                    }
                }));
        titleDialog = titleBuilder.create();

        hintDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("提示：").setMessage("需要存储权限来获取图片")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(true)
                .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("去设置", (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    //这里需要根据手机厂商进行适配，暂时只考虑华为
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                    try {
                        NucleicCodeActivity.this.startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(NucleicCodeActivity.this, "非华为手机暂不支持跳转设置", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    dialogInterface.dismiss();
                });
        hintDialog = hintDialogBuilder.create();

        List<CodeEntity> codeEntities = AppModel.getInstance().loadCodeList();

        viewAdapter = new ViewPagerAdapter(this, codeEntities);
        binding.viewpager.setAdapter(viewAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabPic, binding.viewpager, (tab, position) -> {
            tab.setText(codeEntities.get(position).getTitle());
        });

        mediator.attach();
    }

}