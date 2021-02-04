package com.panel;


import com.other.Constant;
import com.zhihu.other.CommUtils;
import com.zhihu.utils.ClipboardUtils;
import com.zhihu.utils.StringUtil;
import com.zhihu.utils.ZhihuUtils;
import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SwingDemo implements ActionListener {

    private JComboBox<String> cbCategory;

    private JFrame frame = new JFrame("弹框标题");
    private JTextField tfSavePath = new JTextField();
    private JTextField tfArticleLink = new JTextField();
    private JTextField tfCollectionLink = new JTextField();
    private JButton button = new JButton("点击触发事件");

    private SwingDemo() {
        //默认:  文档;社会;历史人物传记;笑一笑;赚钱
        cbCategory = new JComboBox<>(ZhihuUtils.getCategory());

        int labelX = 10, labelY = 10, labelW = 70, fieldX = 80, fieldY = 10, fieldW = 240, height = 20, hSpace = 30;
        int frameW = 350, frameH = 230, btnW = 120;
        double a = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double b = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (a / 2) - 150, (int) (b / 2) - 150));// 设定窗口出现位置
        frame.setSize(frameW, frameH);// 设定窗口大小
        JTabbedPane tabPane = new JTabbedPane();
        frame.setContentPane(tabPane);// 设置布局
        JLabel label1 = new JLabel("默认保存路径:");
        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile()
                        .getAbsolutePath() + File.separator;//这个就是你选择的文件夹的路径
                    System.out.println("filePath = " + filePath);
                    tfSavePath.setText(filePath);
                    CommUtils.setPrivateInfo(Constant.CONFIG_KEY_SAVE_PATH, filePath);
                }
            }
        });
        label1.setBounds(labelX, labelY, labelW, height);
        JLabel label2 = new JLabel("选择保存分类:");
        label2.setBounds(labelX, labelY = labelY + hSpace, labelW, height);
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String inputContent = JOptionPane.showInputDialog(
                    frame, "输入保存分类,用英文\";\"分割", ZhihuUtils.getCategoryStr());
                ZhihuUtils.setCategory(inputContent);
                String[] categorys = ZhihuUtils.getCategory();
                cbCategory.removeAllItems();
                Constant.TAG_ADD_CATEGORY = true;
                for (String category : categorys) {
                    cbCategory.addItem(category);
                }
            }
        });
        JLabel label3 = new JLabel("输入文章链接:");
        label3.setBounds(labelX, labelY = labelY + hSpace, labelW, height);
        JLabel label4 = new JLabel("输入收藏链接:");
        label4.setBounds(labelX, labelY + hSpace, labelW, height);
        tfSavePath.setBounds(fieldX, fieldY, fieldW, height);
        cbCategory.setBounds(fieldX, fieldY = fieldY + hSpace, fieldW, height);
        tfArticleLink.setBounds(fieldX, fieldY = fieldY + hSpace, fieldW, height);
        tfCollectionLink.setBounds(fieldX, fieldY = fieldY + hSpace, fieldW, height);
        button.setBounds((frameW - btnW) / 2, fieldY + hSpace, btnW, height);
        cbCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Constant.TAG_ADD_CATEGORY) {
                    Constant.TAG_ADD_CATEGORY = false;
                    return;
                }
                if (tfCollectionLink.isEnabled() &&
                    !tfCollectionLink.getText().equals(Constant.ENABLE_TXT_TFCOLLECTIONLINK)) {
                    return;
                }
                cbCategory.setPopupVisible(false);
                saveArticleToLocal();
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfCollectionLink.isEnabled() && !StringUtil.isEmpty(tfCollectionLink.getText())
                    && !tfCollectionLink.getText().equals(Constant.ENABLE_TXT_TFCOLLECTIONLINK)) {
                    //下载收藏
                    saveCollectionLink();
                } else {
                    //下载文章
                    saveArticleToLocal();
                }
            }
        });
        Container _ONE = new Container();
        _ONE.add(label1);
        _ONE.add(label2);
        _ONE.add(label3);
        _ONE.add(label4);
        _ONE.add(tfSavePath);
        _ONE.add(cbCategory);
        _ONE.add(tfArticleLink);
        _ONE.add(tfCollectionLink);
        _ONE.add(button);
        frame.setVisible(true);// 窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
        tabPane.add("会话1", _ONE);

        tfSavePath.setText(
            CommUtils.getPrivateInfo(Constant.CONFIG_KEY_SAVE_PATH, Constant.DEF_SAVE_PATH));
//        tfArticleLink.setText("请输入文章链接");
        tfCollectionLink.setText(Constant.ENABLE_TXT_TFCOLLECTIONLINK);
        tfCollectionLink.setEnabled(false);
    }

    public static void main(String[] args) {
        new SwingDemo();
    }

    private void saveCollectionLink() {
        ZhihuUtils.saveCollect(tfSavePath.getText(), tfCollectionLink.getText(),
            (saveMsg, saveSuccess) -> {
                JOptionPane
                    .showMessageDialog(frame,
                        tfCollectionLink.getText() + "\n" + saveMsg,
                        "提示", JOptionPane.WARNING_MESSAGE);

            });
//                System.out.println(tfSavePath.getText() + tfArticleLink.getText() );
    }

    /**
     * 保存文章到本地文件
     */
    private void saveArticleToLocal() {
        if (StringUtil.isBlank(tfSavePath.getText())) {
            JOptionPane.showMessageDialog(frame, "保存路径不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbCategory.getSelectedItem() == null) {
            return;
        }
        String savePath = tfSavePath.getText() + cbCategory.getSelectedItem().toString();
        System.out.println(" 下拉框选择点击条目 " + cbCategory.getSelectedItem().toString());
        //1.剪切板优先
        String articleUrl = ClipboardUtils.getSysClipboardText();
        //2.文章链接
        if (StringUtil.isEmpty(articleUrl)) {
            articleUrl = tfArticleLink.getText();
        }
        //3.保存文章
        ZhihuUtils.saveArticle(savePath, articleUrl,
            (saveName, saveSuccess) -> {
                JOptionPane.showMessageDialog(frame, saveName, "消息提醒", JOptionPane.WARNING_MESSAGE);
            });
        //4.清空文章链接输入框
        tfArticleLink.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource().equals(button)) {frame.dispose();//关闭弹框}
    }
}
