package client.Round_TF_BTN;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.BasicStroke; // BasicStroke 임포트
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class RoundButton extends JButton {
    private Shape shape;
    private int arcWidth = 20;
    private int arcHeight = 20;
    private int borderWidth = 2; // 테두리 두께 추가

    public RoundButton(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        // 버튼의 여백을 늘려 테두리 그릴 공간 확보
        // setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
        // 하지만 이미 setContentAreaFilled(false)로 인해 버튼의 배경을 직접 그리므로,
        // 이 여백은 내부 컴포넌트(아이콘/텍스트)에 영향을 줍니다.
        // 따라서 그리기 시 오프셋을 주는 것이 더 좋습니다.
    }

    public RoundButton() {
        this("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(new Color(200, 200, 200));
        } else if (getModel().isRollover()) {
            g2.setColor(new Color(240, 240, 240));
        } else {
            g2.setColor(getBackground());
        }

        // 배경 그리기: 테두리 두께만큼 안쪽으로 밀어 넣어서 그립니다.
        // 이렇게 하면 테두리가 그려질 공간이 확보됩니다.
        g2.fill(new RoundRectangle2D.Double(
            borderWidth / 2.0, borderWidth / 2.0, // x, y 시작 위치 (테두리 두께의 절반만큼 안쪽으로)
            getWidth() - borderWidth, getHeight() - borderWidth, // 너비, 높이 (테두리 두께만큼 줄임)
            arcWidth, arcHeight
        ));
        
        super.paintComponent(g2); // 텍스트나 아이콘을 그립니다.
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground()); // 테두리 색상
        g2.setStroke(new BasicStroke(borderWidth)); // 테두리 두께 설정

        // 테두리 그리기: 테두리 두께의 절반만큼 안쪽으로 밀어 넣어서 그립니다.
        // 이렇게 해야 경계선이 컴포넌트의 실제 사각형 안에 온전히 그려집니다.
        g2.draw(new RoundRectangle2D.Double(
            borderWidth / 2.0, borderWidth / 2.0, // x, y 시작 위치 (테두리 두께의 절반만큼 안쪽으로)
            getWidth() - borderWidth, getHeight() - borderWidth, // 너비, 높이 (테두리 두께만큼 줄임)
            arcWidth, arcHeight
        ));
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            // contains()는 여전히 전체 컴포넌트 영역을 커버해야 하므로, 테두리 두께만큼 줄이지 않습니다.
            // 하지만 둥근 모서리 자체의 셰이프는 정확해야 합니다.
            shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        }
        return shape.contains(x, y);
    }
}