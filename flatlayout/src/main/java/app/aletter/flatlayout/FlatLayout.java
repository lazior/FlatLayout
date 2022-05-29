package app.aletter.flatlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class FlatLayout extends ConstraintLayout {
    //attributes
    private int BACKGROUND_COLOR;
    private final int[] CORNER_RADIUS_EACH = new int[4];
    private int PADDING_BOTTOM;
    private int PADDING_END;
    private int PADDING_START;
    private int PADDING_TOP;
    private int STROKE_COLOR;
    private int STROKE_WIDTH;

    // parameter
    private int maxCornerRadius;
    private GradientDrawable background;

    public FlatLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public FlatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FlatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setBackgroundColor(int argb) {
        background.setColor(argb);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        // get attributes
        getAttrs(context, attrs);

        // set padding
        int correctPadding = STROKE_WIDTH;
        if (maxCornerRadius > STROKE_WIDTH) {
            correctPadding = maxCornerRadius;
        }
        this.setPadding(
                PADDING_START + correctPadding,
                PADDING_TOP + correctPadding,
                PADDING_END + correctPadding,
                PADDING_BOTTOM + correctPadding
        );

        // set background
        background = createBackground();
        this.setBackground(background);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        int defaultCornerRadius = maxCornerRadius = (int) context.getResources().getDimension(R.dimen.default_corner_radius);
        int defaultPadding = (int) context.getResources().getDimension(R.dimen.default_padding);
        int defaultStrokeWidth = (int) context.getResources().getDimension(R.dimen.default_stroke_width);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlatLayout);

            BACKGROUND_COLOR = typedArray.getColor(R.styleable.FlatLayout_flat_backgroundColor,
                    ContextCompat.getColor(context, android.R.color.transparent));
            int cornerRadius = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_cornerRadius, defaultCornerRadius);
            CORNER_RADIUS_EACH[0] = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_cornerRadiusTopLeft, cornerRadius);
            CORNER_RADIUS_EACH[1] = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_cornerRadiusTopRight, cornerRadius);
            CORNER_RADIUS_EACH[2] = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_cornerRadiusBottomRight, cornerRadius);
            CORNER_RADIUS_EACH[3] = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_cornerRadiusBottomLeft, cornerRadius);
            int padding = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_padding, defaultPadding);
            PADDING_BOTTOM = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_paddingBottom, padding);
            PADDING_END = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_paddingEnd, padding);
            PADDING_START = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_paddingStart, padding);
            PADDING_TOP = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_paddingTop, padding);
            STROKE_COLOR = typedArray.getColor(R.styleable.FlatLayout_flat_strokeColor,
                    ContextCompat.getColor(context, android.R.color.black));
            STROKE_WIDTH = typedArray.getDimensionPixelSize(R.styleable.FlatLayout_flat_strokeWidth, defaultStrokeWidth);

            // get max corner
            for (int i = 0;i < 4;i++) {
                if (CORNER_RADIUS_EACH[i] > maxCornerRadius) {
                    maxCornerRadius = CORNER_RADIUS_EACH[i];
                }
            }

            typedArray.recycle();
        } else {
            BACKGROUND_COLOR = ContextCompat.getColor(context, android.R.color.transparent);
            for (int i = 0;i < 4;i++) {
                CORNER_RADIUS_EACH[i] = defaultCornerRadius;
            }
            PADDING_BOTTOM = defaultPadding;
            PADDING_END = defaultPadding;
            PADDING_START = defaultPadding;
            PADDING_TOP = defaultPadding;
            STROKE_COLOR = ContextCompat.getColor(context, android.R.color.black);
            STROKE_WIDTH = defaultStrokeWidth;
        }
    }

    private GradientDrawable createBackground() {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] {
                CORNER_RADIUS_EACH[0], CORNER_RADIUS_EACH[0], CORNER_RADIUS_EACH[1], CORNER_RADIUS_EACH[1],
                CORNER_RADIUS_EACH[2], CORNER_RADIUS_EACH[2], CORNER_RADIUS_EACH[3], CORNER_RADIUS_EACH[3]
        });
        shape.setColor(BACKGROUND_COLOR);
        shape.setStroke(STROKE_WIDTH, STROKE_COLOR);
        return shape;
    }
}
