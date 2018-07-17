/*
 * (C) Copyright 2016 Kai Burjack

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Helper class to do efficient memory operations on all JOML objects, NIO buffers and primitive arrays.
 * This class is used internally throughout JOML, is undocumented and is subject to change.
 * Use with extreme caution!
 * 
 * @author The LWJGL authors
 * @author Kai Burjack
 */
abstract class MemUtil {
    public static final MemUtil INSTANCE = createInstance();

    private static final MemUtil createInstance() {
        MemUtil accessor;
        try {
            if (Options.NO_UNSAFE)
                accessor = new MemUtilNIO();
            else {
                /* Detect Android version */
                if (android.os.Build.VERSION.SDK_INT <= 23 /* 6, Marshmallow */) {
                    accessor = new MemUtilUnsafe_Marshmallow();
                } else {
                    accessor = new MemUtilUnsafe_Nougat();
                }
            }
        } catch (Throwable e) {
            accessor = new MemUtilNIO();
        }
        return accessor;
    }

    public abstract void put(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void put4x4(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void put4x4(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix3x2f m, int offset, FloatBuffer dest);
    public abstract void put4x4(Matrix3x2f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest);
    public abstract void put4x4(Matrix3x2d m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix3f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix3f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix4d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix4x3d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void putTransposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putfTransposed(Matrix4d m, int offset, FloatBuffer dest);
    public abstract void putfTransposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest);
    public abstract void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix3f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix3d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix3d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix3d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3x2f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix3x2f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3x2d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix3x2d m, int offset, ByteBuffer dest);
    public abstract void put(Vector4d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector4d src, int offset, ByteBuffer dest);
    public abstract void put(Vector4f src, int offset, FloatBuffer dest);
    public abstract void put(Vector4f src, int offset, ByteBuffer dest);
    public abstract void put(Vector4i src, int offset, IntBuffer dest);
    public abstract void put(Vector4i src, int offset, ByteBuffer dest);
    public abstract void put(Vector3f src, int offset, FloatBuffer dest);
    public abstract void put(Vector3f src, int offset, ByteBuffer dest);
    public abstract void put(Vector3d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector3d src, int offset, ByteBuffer dest);
    public abstract void put(Vector3i src, int offset, IntBuffer dest);
    public abstract void put(Vector3i src, int offset, ByteBuffer dest);
    public abstract void put(Vector2f src, int offset, FloatBuffer dest);
    public abstract void put(Vector2f src, int offset, ByteBuffer dest);
    public abstract void put(Vector2d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector2d src, int offset, ByteBuffer dest);
    public abstract void put(Vector2i src, int offset, IntBuffer dest);
    public abstract void put(Vector2i src, int offset, ByteBuffer dest);
    public abstract void get(Matrix4f m, int offset, FloatBuffer src);
    public abstract void get(Matrix4f m, int offset, ByteBuffer src);
    public abstract void get(Matrix4x3f m, int offset, FloatBuffer src);
    public abstract void get(Matrix4x3f m, int offset, ByteBuffer src);
    public abstract void get(Matrix4d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix4d m, int offset, ByteBuffer src);
    public abstract void get(Matrix4x3d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix4x3d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix4d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix4d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix4x3d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix4x3d m, int offset, ByteBuffer src);
    public abstract void get(Matrix3f m, int offset, FloatBuffer src);
    public abstract void get(Matrix3f m, int offset, ByteBuffer src);
    public abstract void get(Matrix3d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix3d m, int offset, ByteBuffer src);
    public abstract void get(Matrix3x2f m, int offset, FloatBuffer src);
    public abstract void get(Matrix3x2f m, int offset, ByteBuffer src);
    public abstract void get(Matrix3x2d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix3x2d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix3d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix3d m, int offset, ByteBuffer src);
    public abstract void get(Vector4d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector4d dst, int offset, ByteBuffer src);
    public abstract void get(Vector4f dst, int offset, FloatBuffer src);
    public abstract void get(Vector4f dst, int offset, ByteBuffer src);
    public abstract void get(Vector4i dst, int offset, IntBuffer src);
    public abstract void get(Vector4i dst, int offset, ByteBuffer src);
    public abstract void get(Vector3f dst, int offset, FloatBuffer src);
    public abstract void get(Vector3f dst, int offset, ByteBuffer src);
    public abstract void get(Vector3d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector3d dst, int offset, ByteBuffer src);
    public abstract void get(Vector3i dst, int offset, IntBuffer src);
    public abstract void get(Vector3i dst, int offset, ByteBuffer src);
    public abstract void get(Vector2f dst, int offset, FloatBuffer src);
    public abstract void get(Vector2f dst, int offset, ByteBuffer src);
    public abstract void get(Vector2d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector2d dst, int offset, ByteBuffer src);
    public abstract void get(Vector2i dst, int offset, IntBuffer src);
    public abstract void get(Vector2i dst, int offset, ByteBuffer src);

    public abstract void copy(Matrix4f src, Matrix4f dest);
    public abstract void copy(Matrix4x3f src, Matrix4x3f dest);
    public abstract void copy(Matrix4f src, Matrix4x3f dest);
    public abstract void copy(Matrix4x3f src, Matrix4f dest);
    public abstract void copy(Matrix3f src, Matrix3f dest);
    public abstract void copy(Matrix3f src, Matrix4f dest);
    public abstract void copy(Matrix4f src, Matrix3f dest);
    public abstract void copy(Matrix3f src, Matrix4x3f dest);
    public abstract void copy(Matrix3x2f src, Matrix3x2f dest);
    public abstract void copy(Matrix3x2d src, Matrix3x2d dest);
    public abstract void copy3x3(Matrix4f src, Matrix4f dest);
    public abstract void copy3x3(Matrix4x3f src, Matrix4x3f dest);
    public abstract void copy3x3(Matrix3f src, Matrix4x3f dest);
    public abstract void copy3x3(Matrix3f src, Matrix4f dest);
    public abstract void copy4x3(Matrix4f src, Matrix4f dest);
    public abstract void copy4x3(Matrix4x3f src, Matrix4f dest);
    public abstract void copy(Vector4f src, Vector4f dst);
    public abstract void copy(Vector4i src, Vector4i dst);
    public abstract void copy(Quaternionf src, Quaternionf dst);
    public abstract void copy(float[] arr, int off, Matrix4f dest);
    public abstract void copy(float[] arr, int off, Matrix3f dest);
    public abstract void copy(float[] arr, int off, Matrix4x3f dest);
    public abstract void copy(float[] arr, int off, Matrix3x2f dest);
    public abstract void copy(double[] arr, int off, Matrix3x2d dest);
    public abstract void copy(Matrix4f src, float[] dest, int off);
    public abstract void copy(Matrix3f src, float[] dest, int off);
    public abstract void copy(Matrix4x3f src, float[] dest, int off);
    public abstract void copy(Matrix3x2f src, float[] dest, int off);
    public abstract void copy(Matrix3x2d src, double[] dest, int off);
    public abstract void copy4x4(Matrix4x3f src, float[] dest, int off);
    public abstract void copy4x4(Matrix3x2f src, float[] dest, int off);
    public abstract void copy4x4(Matrix3x2d src, double[] dest, int off);
    public abstract void identity(Matrix4f dest);
    public abstract void identity(Matrix4x3f dest);
    public abstract void identity(Matrix3f dest);
    public abstract void identity(Matrix3x2f dest);
    public abstract void identity(Matrix3x2d dest);
    public abstract void identity(Quaternionf dest);
    public abstract void swap(Matrix4f m1, Matrix4f m2);
    public abstract void swap(Matrix4x3f m1, Matrix4x3f m2);
    public abstract void swap(Matrix3f m1, Matrix3f m2);
    public abstract void zero(Matrix4f dest);
    public abstract void zero(Matrix4x3f dest);
    public abstract void zero(Matrix3f dest);
    public abstract void zero(Matrix3x2f dest);
    public abstract void zero(Matrix3x2d dest);
    public abstract void zero(Vector4f dest);
    public abstract void zero(Vector4i dest);

    public abstract void putMatrix3f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix3f(Quaternionf q, int position, FloatBuffer dest);
    public abstract void putMatrix4f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix4f(Quaternionf q, int position, FloatBuffer dest);
    public abstract void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest);

    public abstract void set(Matrix4f dest, Vector4f col0, Vector4f col1, Vector4f col2, Vector4f col3);
    public abstract void set(Matrix4x3f dest, Vector3f col0, Vector3f col1, Vector3f col2, Vector3f col3);
    public abstract void set(Matrix3f dest, Vector3f col0, Vector3f col1, Vector3f col2);

    public abstract void putColumn0(Matrix4f src, Vector4f dest);
    public abstract void putColumn1(Matrix4f src, Vector4f dest);
    public abstract void putColumn2(Matrix4f src, Vector4f dest);
    public abstract void putColumn3(Matrix4f src, Vector4f dest);

    public abstract void getColumn0(Matrix4f dest, Vector4f src);
    public abstract void getColumn1(Matrix4f dest, Vector4f src);
    public abstract void getColumn2(Matrix4f dest, Vector4f src);
    public abstract void getColumn3(Matrix4f dest, Vector4f src);

    public abstract void broadcast(float c, Vector4f dest);
    public abstract void broadcast(int c, Vector4i dest);

    public static class MemUtilNIO extends MemUtil {
        private void put0(Matrix4f m, FloatBuffer dest) {
            dest.put(0,  m.m00);
            dest.put(1,  m.m01);
            dest.put(2,  m.m02);
            dest.put(3,  m.m03);
            dest.put(4,  m.m10);
            dest.put(5,  m.m11);
            dest.put(6,  m.m12);
            dest.put(7,  m.m13);
            dest.put(8,  m.m20);
            dest.put(9,  m.m21);
            dest.put(10, m.m22);
            dest.put(11, m.m23);
            dest.put(12, m.m30);
            dest.put(13, m.m31);
            dest.put(14, m.m32);
            dest.put(15, m.m33);
        }
        private void putN(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m03);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  m.m13);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m23);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, m.m33);
        }
        public void put(Matrix4f m, int offset, FloatBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        private void put0(Matrix4f m, ByteBuffer dest) {
            dest.putFloat(0,  m.m00);
            dest.putFloat(4,  m.m01);
            dest.putFloat(8,  m.m02);
            dest.putFloat(12, m.m03);
            dest.putFloat(16, m.m10);
            dest.putFloat(20, m.m11);
            dest.putFloat(24, m.m12);
            dest.putFloat(28, m.m13);
            dest.putFloat(32, m.m20);
            dest.putFloat(36, m.m21);
            dest.putFloat(40, m.m22);
            dest.putFloat(44, m.m23);
            dest.putFloat(48, m.m30);
            dest.putFloat(52, m.m31);
            dest.putFloat(56, m.m32);
            dest.putFloat(60, m.m33);
        }
        private void putN(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m03);
            dest.putFloat(offset+16, m.m10);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m12);
            dest.putFloat(offset+28, m.m13);
            dest.putFloat(offset+32, m.m20);
            dest.putFloat(offset+36, m.m21);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m23);
            dest.putFloat(offset+48, m.m30);
            dest.putFloat(offset+52, m.m31);
            dest.putFloat(offset+56, m.m32);
            dest.putFloat(offset+60, m.m33);
        }
        public void put(Matrix4f m, int offset, ByteBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        private void put0(Matrix4x3f m, FloatBuffer dest) {
            dest.put(0,  m.m00);
            dest.put(1,  m.m01);
            dest.put(2,  m.m02);
            dest.put(3,  m.m10);
            dest.put(4,  m.m11);
            dest.put(5,  m.m12);
            dest.put(6,  m.m20);
            dest.put(7,  m.m21);
            dest.put(8,  m.m22);
            dest.put(9,  m.m30);
            dest.put(10, m.m31);
            dest.put(11, m.m32);
        }
        private void putN(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m10);
            dest.put(offset+4,  m.m11);
            dest.put(offset+5,  m.m12);
            dest.put(offset+6,  m.m20);
            dest.put(offset+7,  m.m21);
            dest.put(offset+8,  m.m22);
            dest.put(offset+9,  m.m30);
            dest.put(offset+10, m.m31);
            dest.put(offset+11, m.m32);
        }
        public void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        private void put0(Matrix4x3f m, ByteBuffer dest) {
            dest.putFloat(0,  m.m00);
            dest.putFloat(4,  m.m01);
            dest.putFloat(8,  m.m02);
            dest.putFloat(12, m.m10);
            dest.putFloat(16, m.m11);
            dest.putFloat(20, m.m12);
            dest.putFloat(24, m.m20);
            dest.putFloat(28, m.m21);
            dest.putFloat(32, m.m22);
            dest.putFloat(36, m.m30);
            dest.putFloat(40, m.m31);
            dest.putFloat(44, m.m32);
        }
        private void putN(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m10);
            dest.putFloat(offset+16, m.m11);
            dest.putFloat(offset+20, m.m12);
            dest.putFloat(offset+24, m.m20);
            dest.putFloat(offset+28, m.m21);
            dest.putFloat(offset+32, m.m22);
            dest.putFloat(offset+36, m.m30);
            dest.putFloat(offset+40, m.m31);
            dest.putFloat(offset+44, m.m32);
        }
        public void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        public void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  0.0f);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  0.0f);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, 0.0f);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, 1.0f);
        }

        public void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, 0.0f);
            dest.putFloat(offset+16, m.m10);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m12);
            dest.putFloat(offset+28, 0.0f);
            dest.putFloat(offset+32, m.m20);
            dest.putFloat(offset+36, m.m21);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, 0.0f);
            dest.putFloat(offset+48, m.m30);
            dest.putFloat(offset+52, m.m31);
            dest.putFloat(offset+56, m.m32);
            dest.putFloat(offset+60, 1.0f);
        }

        public void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  0.0);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  0.0);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, 0.0);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, 1.0);
        }

        public void put4x4(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m01);
            dest.putDouble(offset+8,  m.m02);
            dest.putDouble(offset+12, 0.0);
            dest.putDouble(offset+16, m.m10);
            dest.putDouble(offset+20, m.m11);
            dest.putDouble(offset+24, m.m12);
            dest.putDouble(offset+28, 0.0);
            dest.putDouble(offset+32, m.m20);
            dest.putDouble(offset+36, m.m21);
            dest.putDouble(offset+40, m.m22);
            dest.putDouble(offset+44, 0.0);
            dest.putDouble(offset+48, m.m30);
            dest.putDouble(offset+52, m.m31);
            dest.putDouble(offset+56, m.m32);
            dest.putDouble(offset+60, 1.0);
        }

        public void put4x4(Matrix3x2f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  0.0f);
            dest.put(offset+3,  0.0f);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  0.0f);
            dest.put(offset+7,  0.0f);
            dest.put(offset+8,  0.0f);
            dest.put(offset+9,  0.0f);
            dest.put(offset+10, 1.0f);
            dest.put(offset+11, 0.0f);
            dest.put(offset+12, m.m20);
            dest.put(offset+13, m.m21);
            dest.put(offset+14, 0.0f);
            dest.put(offset+15, 1.0f);
        }

        public void put4x4(Matrix3x2f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  0.0f);
            dest.putFloat(offset+12, 0.0f);
            dest.putFloat(offset+16, m.m10);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, 0.0f);
            dest.putFloat(offset+28, 0.0f);
            dest.putFloat(offset+32, 0.0f);
            dest.putFloat(offset+36, 0.0f);
            dest.putFloat(offset+40, 1.0f);
            dest.putFloat(offset+44, 0.0f);
            dest.putFloat(offset+48, m.m20);
            dest.putFloat(offset+52, m.m21);
            dest.putFloat(offset+56, 0.0f);
            dest.putFloat(offset+60, 1.0f);
        }

        public void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  0.0);
            dest.put(offset+3,  0.0);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  0.0);
            dest.put(offset+7,  0.0);
            dest.put(offset+8,  0.0);
            dest.put(offset+9,  0.0);
            dest.put(offset+10, 1.0);
            dest.put(offset+11, 0.0);
            dest.put(offset+12, m.m20);
            dest.put(offset+13, m.m21);
            dest.put(offset+14, 0.0);
            dest.put(offset+15, 1.0);
        }

        public void put4x4(Matrix3x2d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00);
            dest.putDouble(offset+8,   m.m01);
            dest.putDouble(offset+16,  0.0);
            dest.putDouble(offset+24,  0.0);
            dest.putDouble(offset+32,  m.m10);
            dest.putDouble(offset+40,  m.m11);
            dest.putDouble(offset+48,  0.0);
            dest.putDouble(offset+56,  0.0);
            dest.putDouble(offset+64,  0.0);
            dest.putDouble(offset+72,  0.0);
            dest.putDouble(offset+80,  1.0);
            dest.putDouble(offset+88,  0.0);
            dest.putDouble(offset+96,  m.m20);
            dest.putDouble(offset+104, m.m21);
            dest.putDouble(offset+112, 0.0);
            dest.putDouble(offset+120, 1.0);
        }

        public void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
            dest.put(offset+12, m.m03);
            dest.put(offset+13, m.m13);
            dest.put(offset+14, m.m23);
            dest.put(offset+15, m.m33);
        }

        public void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
            dest.putFloat(offset+48, m.m03);
            dest.putFloat(offset+52, m.m13);
            dest.putFloat(offset+56, m.m23);
            dest.putFloat(offset+60, m.m33);
        }

        public void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        public void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
        }

        public void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        public void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
        }

        public void putTransposed(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m10);
            dest.put(offset+2, m.m20);
            dest.put(offset+3, m.m01);
            dest.put(offset+4, m.m11);
            dest.put(offset+5, m.m21);
            dest.put(offset+6, m.m02);
            dest.put(offset+7, m.m12);
            dest.put(offset+8, m.m22);
        }

        public void putTransposed(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m01);
            dest.putFloat(offset+16, m.m11);
            dest.putFloat(offset+20, m.m21);
            dest.putFloat(offset+24, m.m02);
            dest.putFloat(offset+28, m.m12);
            dest.putFloat(offset+32, m.m22);
        }

        public void put(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m03);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  m.m13);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m23);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, m.m33);
        }

        public void put(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m01);
            dest.putDouble(offset+8,  m.m02);
            dest.putDouble(offset+12, m.m03);
            dest.putDouble(offset+16, m.m10);
            dest.putDouble(offset+20, m.m11);
            dest.putDouble(offset+24, m.m12);
            dest.putDouble(offset+28, m.m13);
            dest.putDouble(offset+32, m.m20);
            dest.putDouble(offset+36, m.m21);
            dest.putDouble(offset+40, m.m22);
            dest.putDouble(offset+44, m.m23);
            dest.putDouble(offset+48, m.m30);
            dest.putDouble(offset+52, m.m31);
            dest.putDouble(offset+56, m.m32);
            dest.putDouble(offset+60, m.m33);
        }

        public void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m10);
            dest.put(offset+4,  m.m11);
            dest.put(offset+5,  m.m12);
            dest.put(offset+6,  m.m20);
            dest.put(offset+7,  m.m21);
            dest.put(offset+8,  m.m22);
            dest.put(offset+9,  m.m30);
            dest.put(offset+10, m.m31);
            dest.put(offset+11, m.m32);
        }

        public void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m01);
            dest.putDouble(offset+8,  m.m02);
            dest.putDouble(offset+12, m.m10);
            dest.putDouble(offset+16, m.m11);
            dest.putDouble(offset+20, m.m12);
            dest.putDouble(offset+24, m.m20);
            dest.putDouble(offset+28, m.m21);
            dest.putDouble(offset+32, m.m22);
            dest.putDouble(offset+36, m.m30);
            dest.putDouble(offset+40, m.m31);
            dest.putDouble(offset+44, m.m32);
        }

        public void putf(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m01);
            dest.put(offset+2,  (float)m.m02);
            dest.put(offset+3,  (float)m.m03);
            dest.put(offset+4,  (float)m.m10);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m12);
            dest.put(offset+7,  (float)m.m13);
            dest.put(offset+8,  (float)m.m20);
            dest.put(offset+9,  (float)m.m21);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m23);
            dest.put(offset+12, (float)m.m30);
            dest.put(offset+13, (float)m.m31);
            dest.put(offset+14, (float)m.m32);
            dest.put(offset+15, (float)m.m33);
        }

        public void putf(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m03);
            dest.putFloat(offset+16, (float)m.m10);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m12);
            dest.putFloat(offset+28, (float)m.m13);
            dest.putFloat(offset+32, (float)m.m20);
            dest.putFloat(offset+36, (float)m.m21);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m23);
            dest.putFloat(offset+48, (float)m.m30);
            dest.putFloat(offset+52, (float)m.m31);
            dest.putFloat(offset+56, (float)m.m32);
            dest.putFloat(offset+60, (float)m.m33);
        }

        public void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m01);
            dest.put(offset+2,  (float)m.m02);
            dest.put(offset+3,  (float)m.m10);
            dest.put(offset+4,  (float)m.m11);
            dest.put(offset+5,  (float)m.m12);
            dest.put(offset+6,  (float)m.m20);
            dest.put(offset+7,  (float)m.m21);
            dest.put(offset+8,  (float)m.m22);
            dest.put(offset+9,  (float)m.m30);
            dest.put(offset+10, (float)m.m31);
            dest.put(offset+11, (float)m.m32);
        }

        public void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m10);
            dest.putFloat(offset+16, (float)m.m11);
            dest.putFloat(offset+20, (float)m.m12);
            dest.putFloat(offset+24, (float)m.m20);
            dest.putFloat(offset+28, (float)m.m21);
            dest.putFloat(offset+32, (float)m.m22);
            dest.putFloat(offset+36, (float)m.m30);
            dest.putFloat(offset+40, (float)m.m31);
            dest.putFloat(offset+44, (float)m.m32);
        }

        public void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
            dest.put(offset+12, m.m03);
            dest.put(offset+13, m.m13);
            dest.put(offset+14, m.m23);
            dest.put(offset+15, m.m33);
        }

        public void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00);
            dest.putDouble(offset+8,   m.m10);
            dest.putDouble(offset+16,  m.m20);
            dest.putDouble(offset+24,  m.m30);
            dest.putDouble(offset+32,  m.m01);
            dest.putDouble(offset+40,  m.m11);
            dest.putDouble(offset+48,  m.m21);
            dest.putDouble(offset+56,  m.m31);
            dest.putDouble(offset+64,  m.m02);
            dest.putDouble(offset+72,  m.m12);
            dest.putDouble(offset+80,  m.m22);
            dest.putDouble(offset+88,  m.m32);
            dest.putDouble(offset+96,  m.m03);
            dest.putDouble(offset+104, m.m13);
            dest.putDouble(offset+112, m.m23);
            dest.putDouble(offset+120, m.m33);
        }

        public void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        public void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00);
            dest.putDouble(offset+8,   m.m10);
            dest.putDouble(offset+16,  m.m20);
            dest.putDouble(offset+24,  m.m30);
            dest.putDouble(offset+32,  m.m01);
            dest.putDouble(offset+40,  m.m11);
            dest.putDouble(offset+48,  m.m21);
            dest.putDouble(offset+56,  m.m31);
            dest.putDouble(offset+64,  m.m02);
            dest.putDouble(offset+72,  m.m12);
            dest.putDouble(offset+80,  m.m22);
            dest.putDouble(offset+88,  m.m32);
        }

        public void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        public void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m10);
            dest.putDouble(offset+8,  m.m20);
            dest.putDouble(offset+12, m.m30);
            dest.putDouble(offset+16, m.m01);
            dest.putDouble(offset+20, m.m11);
            dest.putDouble(offset+24, m.m21);
            dest.putDouble(offset+28, m.m31);
            dest.putDouble(offset+32, m.m02);
            dest.putDouble(offset+36, m.m12);
            dest.putDouble(offset+40, m.m22);
            dest.putDouble(offset+44, m.m32);
        }

        public void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m10);
            dest.put(offset+2,  (float)m.m20);
            dest.put(offset+3,  (float)m.m30);
            dest.put(offset+4,  (float)m.m01);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m21);
            dest.put(offset+7,  (float)m.m31);
            dest.put(offset+8,  (float)m.m02);
            dest.put(offset+9,  (float)m.m12);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m32);
        }

        public void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m10);
            dest.putFloat(offset+8,  (float)m.m20);
            dest.putFloat(offset+12, (float)m.m30);
            dest.putFloat(offset+16, (float)m.m01);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m21);
            dest.putFloat(offset+28, (float)m.m31);
            dest.putFloat(offset+32, (float)m.m02);
            dest.putFloat(offset+36, (float)m.m12);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m32);
        }

        public void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m10);
            dest.put(offset+2,  (float)m.m20);
            dest.put(offset+3,  (float)m.m30);
            dest.put(offset+4,  (float)m.m01);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m21);
            dest.put(offset+7,  (float)m.m31);
            dest.put(offset+8,  (float)m.m02);
            dest.put(offset+9,  (float)m.m12);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m32);
            dest.put(offset+12, (float)m.m03);
            dest.put(offset+13, (float)m.m13);
            dest.put(offset+14, (float)m.m23);
            dest.put(offset+15, (float)m.m33);
        }

        public void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m10);
            dest.putFloat(offset+8,  (float)m.m20);
            dest.putFloat(offset+12, (float)m.m30);
            dest.putFloat(offset+16, (float)m.m01);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m21);
            dest.putFloat(offset+28, (float)m.m31);
            dest.putFloat(offset+32, (float)m.m02);
            dest.putFloat(offset+36, (float)m.m12);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m32);
            dest.putFloat(offset+48, (float)m.m03);
            dest.putFloat(offset+52, (float)m.m13);
            dest.putFloat(offset+56, (float)m.m23);
            dest.putFloat(offset+60, (float)m.m33);
        }

        public void put(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m02);
            dest.put(offset+3, m.m10);
            dest.put(offset+4, m.m11);
            dest.put(offset+5, m.m12);
            dest.put(offset+6, m.m20);
            dest.put(offset+7, m.m21);
            dest.put(offset+8, m.m22);
        }

        public void put(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m10);
            dest.putFloat(offset+16, m.m11);
            dest.putFloat(offset+20, m.m12);
            dest.putFloat(offset+24, m.m20);
            dest.putFloat(offset+28, m.m21);
            dest.putFloat(offset+32, m.m22);
        }

        public void put(Matrix3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m02);
            dest.put(offset+3, m.m10);
            dest.put(offset+4, m.m11);
            dest.put(offset+5, m.m12);
            dest.put(offset+6, m.m20);
            dest.put(offset+7, m.m21);
            dest.put(offset+8, m.m22);
        }

        public void put(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+8,  m.m01);
            dest.putDouble(offset+16, m.m02);
            dest.putDouble(offset+24, m.m10);
            dest.putDouble(offset+32, m.m11);
            dest.putDouble(offset+40, m.m12);
            dest.putDouble(offset+48, m.m20);
            dest.putDouble(offset+56, m.m21);
            dest.putDouble(offset+64, m.m22);
        }

        public void put(Matrix3x2f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m10);
            dest.put(offset+3, m.m11);
            dest.put(offset+4, m.m20);
            dest.put(offset+5, m.m21);
        }

        public void put(Matrix3x2f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m10);
            dest.putFloat(offset+12, m.m11);
            dest.putFloat(offset+16, m.m20);
            dest.putFloat(offset+20, m.m21);
        }

        public void put(Matrix3x2d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m10);
            dest.put(offset+3, m.m11);
            dest.put(offset+4, m.m20);
            dest.put(offset+5, m.m21);
        }

        public void put(Matrix3x2d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+8,  m.m01);
            dest.putDouble(offset+16, m.m10);
            dest.putDouble(offset+24, m.m11);
            dest.putDouble(offset+32, m.m20);
            dest.putDouble(offset+40, m.m21);
        }

        public void putf(Matrix3d m, int offset, FloatBuffer dest) {
            dest.put(offset,   (float)m.m00);
            dest.put(offset+1, (float)m.m01);
            dest.put(offset+2, (float)m.m02);
            dest.put(offset+3, (float)m.m10);
            dest.put(offset+4, (float)m.m11);
            dest.put(offset+5, (float)m.m12);
            dest.put(offset+6, (float)m.m20);
            dest.put(offset+7, (float)m.m21);
            dest.put(offset+8, (float)m.m22);
        }

        public void putf(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m10);
            dest.putFloat(offset+16, (float)m.m11);
            dest.putFloat(offset+20, (float)m.m12);
            dest.putFloat(offset+24, (float)m.m20);
            dest.putFloat(offset+28, (float)m.m21);
            dest.putFloat(offset+32, (float)m.m22);
        }

        public void put(Vector4d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    src.x);
            dest.putDouble(offset+8,  src.y);
            dest.putDouble(offset+16, src.z);
            dest.putDouble(offset+24, src.w);
        }

        public void put(Vector4f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    src.x);
            dest.putFloat(offset+4,  src.y);
            dest.putFloat(offset+8,  src.z);
            dest.putFloat(offset+12, src.w);
        }

        public void put(Vector4i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,    src.x);
            dest.putInt(offset+4,  src.y);
            dest.putInt(offset+8,  src.z);
            dest.putInt(offset+12, src.w);
        }

        public void put(Vector3f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,   src.x);
            dest.putFloat(offset+4, src.y);
            dest.putFloat(offset+8, src.z);
        }

        public void put(Vector3d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    src.x);
            dest.putDouble(offset+8,  src.y);
            dest.putDouble(offset+16, src.z);
        }

        public void put(Vector3i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,   src.x);
            dest.putInt(offset+4, src.y);
            dest.putInt(offset+8, src.z);
        }

        public void put(Vector2f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,   src.x);
            dest.putFloat(offset+4, src.y);
        }

        public void put(Vector2d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,   src.x);
            dest.putDouble(offset+8, src.y);
        }

        public void put(Vector2i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,   src.x);
            dest.putInt(offset+4, src.y);
        }

        public void get(Matrix4f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        public void get(Matrix4f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m03 = src.getFloat(offset+12);
            m.m10 = src.getFloat(offset+16);
            m.m11 = src.getFloat(offset+20);
            m.m12 = src.getFloat(offset+24);
            m.m13 = src.getFloat(offset+28);
            m.m20 = src.getFloat(offset+32);
            m.m21 = src.getFloat(offset+36);
            m.m22 = src.getFloat(offset+40);
            m.m23 = src.getFloat(offset+44);
            m.m30 = src.getFloat(offset+48);
            m.m31 = src.getFloat(offset+52);
            m.m32 = src.getFloat(offset+56);
            m.m33 = src.getFloat(offset+60);
        }

        public void get(Matrix4x3f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        public void get(Matrix4x3f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
            m.m30 = src.getFloat(offset+36);
            m.m31 = src.getFloat(offset+40);
            m.m32 = src.getFloat(offset+44);
        }

        public void get(Matrix4d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        public void get(Matrix4d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m03 = src.getDouble(offset+24);
            m.m10 = src.getDouble(offset+32);
            m.m11 = src.getDouble(offset+40);
            m.m12 = src.getDouble(offset+48);
            m.m13 = src.getDouble(offset+56);
            m.m20 = src.getDouble(offset+64);
            m.m21 = src.getDouble(offset+72);
            m.m22 = src.getDouble(offset+80);
            m.m23 = src.getDouble(offset+88);
            m.m30 = src.getDouble(offset+96);
            m.m31 = src.getDouble(offset+104);
            m.m32 = src.getDouble(offset+112);
            m.m33 = src.getDouble(offset+120);
        }

        public void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        public void get(Matrix4x3d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m10 = src.getDouble(offset+24);
            m.m11 = src.getDouble(offset+32);
            m.m12 = src.getDouble(offset+40);
            m.m20 = src.getDouble(offset+48);
            m.m21 = src.getDouble(offset+56);
            m.m22 = src.getDouble(offset+64);
            m.m30 = src.getDouble(offset+72);
            m.m31 = src.getDouble(offset+80);
            m.m32 = src.getDouble(offset+88);
        }

        public void getf(Matrix4d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        public void getf(Matrix4d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m03 = src.getFloat(offset+12);
            m.m10 = src.getFloat(offset+16);
            m.m11 = src.getFloat(offset+20);
            m.m12 = src.getFloat(offset+24);
            m.m13 = src.getFloat(offset+28);
            m.m20 = src.getFloat(offset+32);
            m.m21 = src.getFloat(offset+36);
            m.m22 = src.getFloat(offset+40);
            m.m23 = src.getFloat(offset+44);
            m.m30 = src.getFloat(offset+48);
            m.m31 = src.getFloat(offset+52);
            m.m32 = src.getFloat(offset+56);
            m.m33 = src.getFloat(offset+60);
        }

        public void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        public void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
            m.m30 = src.getFloat(offset+36);
            m.m31 = src.getFloat(offset+40);
            m.m32 = src.getFloat(offset+44);
        }

        public void get(Matrix3f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        public void get(Matrix3f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
        }

        public void get(Matrix3d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        public void get(Matrix3d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m10 = src.getDouble(offset+24);
            m.m11 = src.getDouble(offset+32);
            m.m12 = src.getDouble(offset+40);
            m.m20 = src.getDouble(offset+48);
            m.m21 = src.getDouble(offset+56);
            m.m22 = src.getDouble(offset+64);
        }

        public void get(Matrix3x2f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m10 = src.get(offset+2);
            m.m11 = src.get(offset+3);
            m.m20 = src.get(offset+4);
            m.m21 = src.get(offset+5);
        }

        public void get(Matrix3x2f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m10 = src.getFloat(offset+8);
            m.m11 = src.getFloat(offset+12);
            m.m20 = src.getFloat(offset+16);
            m.m21 = src.getFloat(offset+20);
        }

        public void get(Matrix3x2d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m10 = src.get(offset+2);
            m.m11 = src.get(offset+3);
            m.m20 = src.get(offset+4);
            m.m21 = src.get(offset+5);
        }

        public void get(Matrix3x2d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m10 = src.getDouble(offset+16);
            m.m11 = src.getDouble(offset+24);
            m.m20 = src.getDouble(offset+32);
            m.m21 = src.getDouble(offset+40);
        }

        public void getf(Matrix3d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        public void getf(Matrix3d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
        }

        public void get(Vector4d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
            dst.z = src.getDouble(offset+16);
            dst.w = src.getDouble(offset+24);
        }

        public void get(Vector4f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
            dst.z = src.getFloat(offset+8);
            dst.w = src.getFloat(offset+12);
        }

        public void get(Vector4i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
            dst.z = src.getInt(offset+8);
            dst.w = src.getInt(offset+12);
        }

        public void get(Vector3f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
            dst.z = src.getFloat(offset+8);
        }

        public void get(Vector3d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
            dst.z = src.getDouble(offset+16);
        }

        public void get(Vector3i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
            dst.z = src.getInt(offset+8);
        }

        public void get(Vector2f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
        }

        public void get(Vector2d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
        }

        public void get(Vector2i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
        }

        public void copy(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m03 = src.m03;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m13 = src.m13;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m23 = src.m23;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
            dest.m33 = src.m33;
        }

        public void copy(Matrix3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m03 = 0.0f;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m13 = 0.0f;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 1.0f;
        }

        public void copy(Matrix4f src, Matrix3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy(Matrix3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        public void copy(Matrix3x2f src, Matrix3x2f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
        }

        public void copy(Matrix3x2d src, Matrix3x2d dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
        }

        public void copy3x3(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy3x3(Matrix4x3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy3x3(Matrix3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy3x3(Matrix3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy4x3(Matrix4x3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        public void copy(Vector4f src, Vector4f dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy(Vector4i src, Vector4i dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy(Quaternionf src, Quaternionf dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy4x3(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        public void copy(Matrix4f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        public void copy(Matrix4x3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m03 = 0.0f;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m13 = 0.0f;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m23 = 0.0f;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
            dest.m33 = 1.0f;
        }

        public void copy(Matrix4x3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        public void copy(Matrix3f src, Matrix3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        public void copy(float[] arr, int off, Matrix4f dest) {
            dest.m00 = arr[off+0];
            dest.m01 = arr[off+1];
            dest.m02 = arr[off+2];
            dest.m03 = arr[off+3];
            dest.m10 = arr[off+4];
            dest.m11 = arr[off+5];
            dest.m12 = arr[off+6];
            dest.m13 = arr[off+7];
            dest.m20 = arr[off+8];
            dest.m21 = arr[off+9];
            dest.m22 = arr[off+10];
            dest.m23 = arr[off+11];
            dest.m30 = arr[off+12];
            dest.m31 = arr[off+13];
            dest.m32 = arr[off+14];
            dest.m33 = arr[off+15];
        }

        public void copy(float[] arr, int off, Matrix3f dest) {
            dest.m00 = arr[off+0];
            dest.m01 = arr[off+1];
            dest.m02 = arr[off+2];
            dest.m10 = arr[off+3];
            dest.m11 = arr[off+4];
            dest.m12 = arr[off+5];
            dest.m20 = arr[off+6];
            dest.m21 = arr[off+7];
            dest.m22 = arr[off+8];
        }

        public void copy(float[] arr, int off, Matrix4x3f dest) {
            dest.m00 = arr[off+0];
            dest.m01 = arr[off+1];
            dest.m02 = arr[off+2];
            dest.m10 = arr[off+3];
            dest.m11 = arr[off+4];
            dest.m12 = arr[off+5];
            dest.m20 = arr[off+6];
            dest.m21 = arr[off+7];
            dest.m22 = arr[off+8];
            dest.m30 = arr[off+9];
            dest.m31 = arr[off+10];
            dest.m32 = arr[off+11];
        }

        public void copy(float[] arr, int off, Matrix3x2f dest) {
            dest.m00 = arr[off+0];
            dest.m01 = arr[off+1];
            dest.m10 = arr[off+2];
            dest.m11 = arr[off+3];
            dest.m20 = arr[off+4];
            dest.m21 = arr[off+5];
        }

        public void copy(double[] arr, int off, Matrix3x2d dest) {
            dest.m00 = arr[off+0];
            dest.m01 = arr[off+1];
            dest.m10 = arr[off+2];
            dest.m11 = arr[off+3];
            dest.m20 = arr[off+4];
            dest.m21 = arr[off+5];
        }

        public void copy(Matrix4f src, float[] dest, int off) {
            dest[off+0]  = src.m00;
            dest[off+1]  = src.m01;
            dest[off+2]  = src.m02;
            dest[off+3]  = src.m03;
            dest[off+4]  = src.m10;
            dest[off+5]  = src.m11;
            dest[off+6]  = src.m12;
            dest[off+7]  = src.m13;
            dest[off+8]  = src.m20;
            dest[off+9]  = src.m21;
            dest[off+10] = src.m22;
            dest[off+11] = src.m23;
            dest[off+12] = src.m30;
            dest[off+13] = src.m31;
            dest[off+14] = src.m32;
            dest[off+15] = src.m33;
        }

        public void copy(Matrix3f src, float[] dest, int off) {
            dest[off+0] = src.m00;
            dest[off+1] = src.m01;
            dest[off+2] = src.m02;
            dest[off+3] = src.m10;
            dest[off+4] = src.m11;
            dest[off+5] = src.m12;
            dest[off+6] = src.m20;
            dest[off+7] = src.m21;
            dest[off+8] = src.m22;
        }

        public void copy(Matrix4x3f src, float[] dest, int off) {
            dest[off+0]  = src.m00;
            dest[off+1]  = src.m01;
            dest[off+2]  = src.m02;
            dest[off+3]  = src.m10;
            dest[off+4]  = src.m11;
            dest[off+5]  = src.m12;
            dest[off+6]  = src.m20;
            dest[off+7]  = src.m21;
            dest[off+8]  = src.m22;
            dest[off+9]  = src.m30;
            dest[off+10] = src.m31;
            dest[off+11] = src.m32;
        }

        public void copy(Matrix3x2f src, float[] dest, int off) {
            dest[off+0] = src.m00;
            dest[off+1] = src.m01;
            dest[off+2] = src.m10;
            dest[off+3] = src.m11;
            dest[off+4] = src.m20;
            dest[off+5] = src.m21;
        }

        public void copy(Matrix3x2d src, double[] dest, int off) {
            dest[off+0] = src.m00;
            dest[off+1] = src.m01;
            dest[off+2] = src.m10;
            dest[off+3] = src.m11;
            dest[off+4] = src.m20;
            dest[off+5] = src.m21;
        }

        public void copy4x4(Matrix4x3f src, float[] dest, int off) {
            dest[off+0]  = src.m00;
            dest[off+1]  = src.m01;
            dest[off+2]  = src.m02;
            dest[off+3]  = 0.0f;
            dest[off+4]  = src.m10;
            dest[off+5]  = src.m11;
            dest[off+6]  = src.m12;
            dest[off+7]  = 0.0f;
            dest[off+8]  = src.m20;
            dest[off+9]  = src.m21;
            dest[off+10] = src.m22;
            dest[off+11] = 0.0f;
            dest[off+12] = src.m30;
            dest[off+13] = src.m31;
            dest[off+14] = src.m32;
            dest[off+15] = 1.0f;
        }

        public void copy4x4(Matrix3x2f src, float[] dest, int off) {
            dest[off+0]  = src.m00;
            dest[off+1]  = src.m01;
            dest[off+2]  = 0.0f;
            dest[off+3]  = 0.0f;
            dest[off+4]  = src.m10;
            dest[off+5]  = src.m11;
            dest[off+6]  = 0.0f;
            dest[off+7]  = 0.0f;
            dest[off+8]  = 0.0f;
            dest[off+9]  = 0.0f;
            dest[off+10] = 1.0f;
            dest[off+11] = 0.0f;
            dest[off+12] = src.m20;
            dest[off+13] = src.m21;
            dest[off+14] = 0.0f;
            dest[off+15] = 1.0f;
        }

        public void copy4x4(Matrix3x2d src, double[] dest, int off) {
            dest[off+0]  = src.m00;
            dest[off+1]  = src.m01;
            dest[off+2]  = 0.0;
            dest[off+3]  = 0.0;
            dest[off+4]  = src.m10;
            dest[off+5]  = src.m11;
            dest[off+6]  = 0.0;
            dest[off+7]  = 0.0;
            dest[off+8]  = 0.0;
            dest[off+9]  = 0.0;
            dest[off+10] = 1.0;
            dest[off+11] = 0.0;
            dest[off+12] = src.m20;
            dest[off+13] = src.m21;
            dest[off+14] = 0.0;
            dest[off+15] = 1.0;
        }

        public void identity(Matrix4f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m03 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m13 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 1.0f;
        }

        public void identity(Matrix4x3f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        public void identity(Matrix3f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
        }

        public void identity(Matrix3x2f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
        }

        public void identity(Matrix3x2d dest) {
            dest.m00 = 1.0;
            dest.m01 = 0.0;
            dest.m10 = 0.0;
            dest.m11 = 1.0;
            dest.m20 = 0.0;
            dest.m21 = 0.0;
        }

        public void identity(Quaternionf dest) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 1.0f;
        }

        public void swap(Matrix4f m1, Matrix4f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m03; m1.m03 = m2.m03; m2.m03 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m13; m1.m13 = m2.m13; m2.m13 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
            tmp = m1.m23; m1.m23 = m2.m23; m2.m23 = tmp;
            tmp = m1.m30; m1.m30 = m2.m30; m2.m30 = tmp;
            tmp = m1.m31; m1.m31 = m2.m31; m2.m31 = tmp;
            tmp = m1.m32; m1.m32 = m2.m32; m2.m32 = tmp;
            tmp = m1.m33; m1.m33 = m2.m33; m2.m33 = tmp;
        }

        public void swap(Matrix4x3f m1, Matrix4x3f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
            tmp = m1.m30; m1.m30 = m2.m30; m2.m30 = tmp;
            tmp = m1.m31; m1.m31 = m2.m31; m2.m31 = tmp;
            tmp = m1.m32; m1.m32 = m2.m32; m2.m32 = tmp;
        }
        
        public void swap(Matrix3f m1, Matrix3f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
        }

        public void zero(Matrix4f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m03 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m13 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 0.0f;
        }

        public void zero(Matrix4x3f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        public void zero(Matrix3f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
        }

        public void zero(Matrix3x2f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
        }

        public void zero(Matrix3x2d dest) {
            dest.m00 = 0.0;
            dest.m01 = 0.0;
            dest.m10 = 0.0;
            dest.m11 = 0.0;
            dest.m20 = 0.0;
            dest.m21 = 0.0;
        }

        public void zero(Vector4f dest) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 0.0f;
        }

        public void zero(Vector4i dest) {
            dest.x = 0;
            dest.y = 0;
            dest.z = 0;
            dest.w = 0;
        }

        public void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, -zw + xy - zw + xy);
            dest.putFloat(position + 16, y2 - z2 + w2 - x2);
            dest.putFloat(position + 20, yz + yz + xw + xw);
            dest.putFloat(position + 24, yw + xz + xz + yw);
            dest.putFloat(position + 28, yz + yz - xw - xw);
            dest.putFloat(position + 32, z2 - y2 - x2 + w2);
        }

        public void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, -zw + xy - zw + xy);
            dest.put(position + 4, y2 - z2 + w2 - x2);
            dest.put(position + 5, yz + yz + xw + xw);
            dest.put(position + 6, yw + xz + xz + yw);
            dest.put(position + 7, yz + yz - xw - xw);
            dest.put(position + 8, z2 - y2 - x2 + w2);
        }

        public void putMatrix4f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, 0.0f);
            dest.putFloat(position + 16, -zw + xy - zw + xy);
            dest.putFloat(position + 20, y2 - z2 + w2 - x2);
            dest.putFloat(position + 24, yz + yz + xw + xw);
            dest.putFloat(position + 28, 0.0f);
            dest.putFloat(position + 32, yw + xz + xz + yw);
            dest.putFloat(position + 36, yz + yz - xw - xw);
            dest.putFloat(position + 40, z2 - y2 - x2 + w2);
            dest.putFloat(position + 44, 0.0f);
            dest.putLong(position + 48, 0L);
            dest.putLong(position + 56, 0x3F80000000000000L);
        }

        public void putMatrix4f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, 0.0f);
            dest.put(position + 4, -zw + xy - zw + xy);
            dest.put(position + 5, y2 - z2 + w2 - x2);
            dest.put(position + 6, yz + yz + xw + xw);
            dest.put(position + 7, 0.0f);
            dest.put(position + 8, yw + xz + xz + yw);
            dest.put(position + 9, yz + yz - xw - xw);
            dest.put(position + 10, z2 - y2 - x2 + w2);
            dest.put(position + 11, 0.0f);
            dest.put(position + 12, 0.0f);
            dest.put(position + 13, 0.0f);
            dest.put(position + 14, 0.0f);
            dest.put(position + 15, 1.0f);
        }

        public void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, -zw + xy - zw + xy);
            dest.putFloat(position + 16, y2 - z2 + w2 - x2);
            dest.putFloat(position + 20, yz + yz + xw + xw);
            dest.putFloat(position + 24, yw + xz + xz + yw);
            dest.putFloat(position + 28, yz + yz - xw - xw);
            dest.putFloat(position + 32, z2 - y2 - x2 + w2);
            dest.putLong(position + 36, 0L);
            dest.putFloat(position + 44, 0.0f);
        }

        public void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, -zw + xy - zw + xy);
            dest.put(position + 4, y2 - z2 + w2 - x2);
            dest.put(position + 5, yz + yz + xw + xw);
            dest.put(position + 6, yw + xz + xz + yw);
            dest.put(position + 7, yz + yz - xw - xw);
            dest.put(position + 8, z2 - y2 - x2 + w2);
            dest.put(position + 9, 0.0f);
            dest.put(position + 10, 0.0f);
            dest.put(position + 11, 0.0f);
        }

        public void set(Matrix4f m, Vector4f col0, Vector4f col1, Vector4f col2, Vector4f col3) {
            m.m00 = col0.x;
            m.m01 = col0.y;
            m.m02 = col0.z;
            m.m03 = col0.w;
            m.m10 = col1.x;
            m.m11 = col1.y;
            m.m12 = col1.z;
            m.m13 = col1.w;
            m.m20 = col2.x;
            m.m21 = col2.y;
            m.m22 = col2.z;
            m.m23 = col2.w;
            m.m30 = col3.x;
            m.m31 = col3.y;
            m.m32 = col3.z;
            m.m33 = col3.w;
        }

        public void set(Matrix4x3f m, Vector3f col0, Vector3f col1, Vector3f col2, Vector3f col3) {
            m.m00 = col0.x;
            m.m01 = col0.y;
            m.m02 = col0.z;
            m.m10 = col1.x;
            m.m11 = col1.y;
            m.m12 = col1.z;
            m.m20 = col2.x;
            m.m21 = col2.y;
            m.m22 = col2.z;
            m.m30 = col3.x;
            m.m31 = col3.y;
            m.m32 = col3.z;
        }

        public void set(Matrix3f m, Vector3f col0, Vector3f col1, Vector3f col2) {
            m.m00 = col0.x;
            m.m01 = col0.y;
            m.m02 = col0.z;
            m.m10 = col1.x;
            m.m11 = col1.y;
            m.m12 = col1.z;
            m.m20 = col2.x;
            m.m21 = col2.y;
            m.m22 = col2.z;
        }

        public void putColumn0(Matrix4f src, Vector4f dest) {
            dest.x = src.m00;
            dest.y = src.m01;
            dest.z = src.m02;
            dest.w = src.m03;
        }

        public void putColumn1(Matrix4f src, Vector4f dest) {
            dest.x = src.m10;
            dest.y = src.m11;
            dest.z = src.m12;
            dest.w = src.m13;
        }

        public void putColumn2(Matrix4f src, Vector4f dest) {
            dest.x = src.m20;
            dest.y = src.m21;
            dest.z = src.m22;
            dest.w = src.m23;
        }

        public void putColumn3(Matrix4f src, Vector4f dest) {
            dest.x = src.m30;
            dest.y = src.m31;
            dest.z = src.m32;
            dest.w = src.m33;
        }

        public void getColumn0(Matrix4f dest, Vector4f src) {
            dest.m00 = src.x;
            dest.m01 = src.y;
            dest.m02 = src.z;
            dest.m03 = src.w;
        }

        public void getColumn1(Matrix4f dest, Vector4f src) {
            dest.m10 = src.x;
            dest.m11 = src.y;
            dest.m12 = src.z;
            dest.m13 = src.w;
        }

        public void getColumn2(Matrix4f dest, Vector4f src) {
            dest.m20 = src.x;
            dest.m21 = src.y;
            dest.m22 = src.z;
            dest.m23 = src.w;
        }

        public void getColumn3(Matrix4f dest, Vector4f src) {
            dest.m30 = src.x;
            dest.m31 = src.y;
            dest.m32 = src.z;
            dest.m33 = src.w;
        }

        public void broadcast(float c, Vector4f dest) {
            dest.x = c;
            dest.y = c;
            dest.z = c;
            dest.w = c;
        }

        public void broadcast(int c, Vector4i dest) {
            dest.x = c;
            dest.y = c;
            dest.z = c;
            dest.w = c;
        }
    }

    public static class MemUtilUnsafe_Nougat extends MemUtilNIO {
        protected static final sun.misc.Unsafe UNSAFE;
        private static final long ADDRESS;
        protected static final long Matrix3f_m00;
        protected static final long Matrix4f_m00;
        protected static final long Matrix4x3f_m00;
        protected static final long Matrix3x2f_m00;

        static {
            UNSAFE = getUnsafeInstance();
            try {
                ADDRESS = UNSAFE.objectFieldOffset(findBufferAddressField());
                Matrix4f_m00 = checkMatrix4f();
                Matrix4x3f_m00 = checkMatrix4x3f();
                Matrix3f_m00 = checkMatrix3f();
                Matrix3x2f_m00 = checkMatrix3x2f();
                // Check if we can use object field offset/address put/get methods
                sun.misc.Unsafe.class.getDeclaredMethod("getLong", new Class[] {Object.class, long.class});
                sun.misc.Unsafe.class.getDeclaredMethod("putLong", new Class[] {Object.class, long.class, long.class});
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException();
            } catch (NoSuchMethodException e) {
                throw new UnsupportedOperationException();
            }
        }

        private static final Field findBufferAddressField() throws NoSuchFieldException {
            Field field;
            try {
                field = getDeclaredField(Buffer.class, "effectiveDirectAddress");
            } catch (NoSuchFieldException e) {
                field = getDeclaredField(Buffer.class, "address");
            }
            return field;
        }

        private static long checkMatrix4f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix4f.class.getDeclaredField("m00");
            long Matrix4f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 16; i++) {
                int c = i >>> 2;
                int r = i & 3;
                f = Matrix4f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix4f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix4f_m00;
        }

        private static long checkMatrix4x3f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix4x3f.class.getDeclaredField("m00");
            long Matrix4x3f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 12; i++) {
                int c = i / 3;
                int r = i % 3;
                f = Matrix4x3f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix4x3f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix4x3f_m00;
        }

        private static long checkMatrix3f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix3f.class.getDeclaredField("m00");
            long Matrix3f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 9; i++) {
                int c = i / 3;
                int r = i % 3;
                f = Matrix3f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix3f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix3f_m00;
        }

        private static long checkMatrix3x2f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix3x2f.class.getDeclaredField("m00");
            long Matrix3x2f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 6; i++) {
                int c = i / 2;
                int r = i % 2;
                f = Matrix3x2f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix3x2f_m00 + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Matrix3x2f element offset");
            }
            return Matrix3x2f_m00;
        }

        private static final Field getDeclaredField(Class root, String fieldName) throws NoSuchFieldException {
            Class type = root;
            do {
                try {
                    Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException e) {
                    type = type.getSuperclass();
                } catch (SecurityException e) {
                    type = type.getSuperclass();
                }
            } while (type != null);
            throw new NoSuchFieldException(fieldName + " does not exist in " + root.getName() + " or any of its superclasses."); //$NON-NLS-1$ //$NON-NLS-2$
        }

        private static final sun.misc.Unsafe getUnsafeInstance() throws SecurityException {
            Field[] fields = sun.misc.Unsafe.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!field.getType().equals(sun.misc.Unsafe.class))
                    continue;
                int modifiers = field.getModifiers();
                if (!(java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers)))
                    continue;
                field.setAccessible(true);
                try {
                    return (sun.misc.Unsafe) field.get(null);
                } catch (IllegalAccessException e) {
                    /* Ignore */
                }
                break;
            }
            throw new UnsupportedOperationException();
        }

        public final long addressOf(Buffer buffer) {
            return UNSAFE.getLong(buffer, ADDRESS);
        }

        private void putDouble(long addr, double dbl) {
        	UNSAFE.putLong(null, addr, Double.doubleToRawLongBits(dbl));
        }

        protected void putFloat(long addr, float flt) {
        	UNSAFE.putInt(null, addr, Float.floatToRawIntBits(flt));
        }

        private double getDouble(long addr) {
        	return Double.longBitsToDouble(UNSAFE.getLong(null, addr));
        }

        private float getFloat(long addr) {
        	return Float.intBitsToFloat(UNSAFE.getInt(null, addr));
        }

        public void put(Matrix4f m, long destAddr) {
            putFloat(destAddr,    m.m00);
            putFloat(destAddr+4,  m.m01);
            putFloat(destAddr+8,  m.m02);
            putFloat(destAddr+12, m.m03);
            putFloat(destAddr+16, m.m10);
            putFloat(destAddr+20, m.m11);
            putFloat(destAddr+24, m.m12);
            putFloat(destAddr+28, m.m13);
            putFloat(destAddr+32, m.m20);
            putFloat(destAddr+36, m.m21);
            putFloat(destAddr+40, m.m22);
            putFloat(destAddr+44, m.m23);
            putFloat(destAddr+48, m.m30);
            putFloat(destAddr+52, m.m31);
            putFloat(destAddr+56, m.m32);
            putFloat(destAddr+60, m.m33);
        }

        public void put(Matrix4x3f m, long destAddr) {
            putFloat(destAddr,    m.m00);
            putFloat(destAddr+4,  m.m01);
            putFloat(destAddr+8,  m.m02);
            putFloat(destAddr+12, m.m10);
            putFloat(destAddr+16, m.m11);
            putFloat(destAddr+20, m.m12);
            putFloat(destAddr+24, m.m20);
            putFloat(destAddr+28, m.m21);
            putFloat(destAddr+32, m.m22);
            putFloat(destAddr+36, m.m30);
            putFloat(destAddr+40, m.m31);
            putFloat(destAddr+44, m.m32);
        }

        public void put4x4(Matrix3x2f m, long destAddr) {
            UNSAFE.putLong(null, destAddr,    UNSAFE.getLong(m, Matrix3x2f_m00));
            UNSAFE.putLong(null, destAddr+8,  0L);
            UNSAFE.putLong(null, destAddr+16, UNSAFE.getLong(m, Matrix3x2f_m00+8));
            UNSAFE.putLong(null, destAddr+24, 0L);
            UNSAFE.putLong(null, destAddr+32, 0L);
            UNSAFE.putLong(null, destAddr+40, 0x3F800000L);
            UNSAFE.putLong(null, destAddr+48, UNSAFE.getLong(m, Matrix3x2f_m00+16));
            UNSAFE.putLong(null, destAddr+56, 0x3F80000000000000L);
        }

        public void put4x4(Matrix3x2d m, long destAddr) {
            putDouble(destAddr,       m.m00);
            putDouble(destAddr + 8,   m.m01);
            putDouble(destAddr + 16,  0.0);
            putDouble(destAddr + 24,  0.0);
            putDouble(destAddr + 32,  m.m10);
            putDouble(destAddr + 40,  m.m11);
            putDouble(destAddr + 48,  0.0);
            putDouble(destAddr + 56,  0.0);
            putDouble(destAddr + 64,  0.0);
            putDouble(destAddr + 72,  0.0);
            putDouble(destAddr + 80,  1.0);
            putDouble(destAddr + 88,  0.0);
            putDouble(destAddr + 96,  m.m20);
            putDouble(destAddr + 104, m.m21);
            putDouble(destAddr + 112, 0.0);
            putDouble(destAddr + 120, 1.0);
        }

        public void put4x4(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putLong(null, destAddr + (i << 4), UNSAFE.getLong(m, Matrix4x3f_m00 + 12 * i));
                long lng = UNSAFE.getInt(m, Matrix4x3f_m00 + 8 + 12 * i) & 0xFFFFFFFFL;
                UNSAFE.putLong(null, destAddr + 8 + (i << 4), lng);
            }
            putFloat(destAddr + 60, 1.0f);
        }

        public void put4x4(Matrix4x3d m, long destAddr) {
            putDouble(destAddr,       m.m00);
            putDouble(destAddr + 8,   m.m01);
            putDouble(destAddr + 16,  m.m02);
            putDouble(destAddr + 24,  0.0);
            putDouble(destAddr + 32,  m.m10);
            putDouble(destAddr + 40,  m.m11);
            putDouble(destAddr + 48,  m.m12);
            putDouble(destAddr + 56,  0.0);
            putDouble(destAddr + 64,  m.m20);
            putDouble(destAddr + 72,  m.m21);
            putDouble(destAddr + 80,  m.m22);
            putDouble(destAddr + 88,  0.0);
            putDouble(destAddr + 96,  m.m30);
            putDouble(destAddr + 104, m.m31);
            putDouble(destAddr + 112, m.m32);
            putDouble(destAddr + 120, 1.0);
        }

        public void putTransposed(Matrix4f m, long destAddr) {
            putFloat(destAddr,      m.m00);
            putFloat(destAddr + 4,  m.m10);
            putFloat(destAddr + 8,  m.m20);
            putFloat(destAddr + 12, m.m30);
            putFloat(destAddr + 16, m.m01);
            putFloat(destAddr + 20, m.m11);
            putFloat(destAddr + 24, m.m21);
            putFloat(destAddr + 28, m.m31);
            putFloat(destAddr + 32, m.m02);
            putFloat(destAddr + 36, m.m12);
            putFloat(destAddr + 40, m.m22);
            putFloat(destAddr + 44, m.m32);
            putFloat(destAddr + 48, m.m03);
            putFloat(destAddr + 52, m.m13);
            putFloat(destAddr + 56, m.m23);
            putFloat(destAddr + 60, m.m33);
        }

        public void put4x3Transposed(Matrix4f m, long destAddr) {
            putFloat(destAddr,      m.m00);
            putFloat(destAddr + 4,  m.m10);
            putFloat(destAddr + 8,  m.m20);
            putFloat(destAddr + 12, m.m30);
            putFloat(destAddr + 16, m.m01);
            putFloat(destAddr + 20, m.m11);
            putFloat(destAddr + 24, m.m21);
            putFloat(destAddr + 28, m.m31);
            putFloat(destAddr + 32, m.m02);
            putFloat(destAddr + 36, m.m12);
            putFloat(destAddr + 40, m.m22);
            putFloat(destAddr + 44, m.m32);
        }

        public void putTransposed(Matrix4x3f m, long destAddr) {
            putFloat(destAddr,      m.m00);
            putFloat(destAddr + 4,  m.m10);
            putFloat(destAddr + 8,  m.m20);
            putFloat(destAddr + 12, m.m30);
            putFloat(destAddr + 16, m.m01);
            putFloat(destAddr + 20, m.m11);
            putFloat(destAddr + 24, m.m21);
            putFloat(destAddr + 28, m.m31);
            putFloat(destAddr + 32, m.m02);
            putFloat(destAddr + 36, m.m12);
            putFloat(destAddr + 40, m.m22);
            putFloat(destAddr + 44, m.m32);
        }

        public void putTransposed(Matrix3f m, long destAddr) {
            putFloat(destAddr,      m.m00);
            putFloat(destAddr + 4,  m.m10);
            putFloat(destAddr + 8,  m.m20);
            putFloat(destAddr + 12, m.m01);
            putFloat(destAddr + 16, m.m11);
            putFloat(destAddr + 20, m.m21);
            putFloat(destAddr + 24, m.m02);
            putFloat(destAddr + 28, m.m12);
            putFloat(destAddr + 32, m.m22);
        }

        public void put(Matrix4d m, long destAddr) {
            putDouble(destAddr,       m.m00);
            putDouble(destAddr + 8,   m.m01);
            putDouble(destAddr + 16,  m.m02);
            putDouble(destAddr + 24,  m.m03);
            putDouble(destAddr + 32,  m.m10);
            putDouble(destAddr + 40,  m.m11);
            putDouble(destAddr + 48,  m.m12);
            putDouble(destAddr + 56,  m.m13);
            putDouble(destAddr + 64,  m.m20);
            putDouble(destAddr + 72,  m.m21);
            putDouble(destAddr + 80,  m.m22);
            putDouble(destAddr + 88,  m.m23);
            putDouble(destAddr + 96,  m.m30);
            putDouble(destAddr + 104, m.m31);
            putDouble(destAddr + 112, m.m32);
            putDouble(destAddr + 120, m.m33);
        }

        public void put(Matrix4x3d m, long destAddr) {
            putDouble(destAddr,      m.m00);
            putDouble(destAddr + 8,  m.m01);
            putDouble(destAddr + 16, m.m02);
            putDouble(destAddr + 24, m.m10);
            putDouble(destAddr + 32, m.m11);
            putDouble(destAddr + 40, m.m12);
            putDouble(destAddr + 48, m.m20);
            putDouble(destAddr + 56, m.m21);
            putDouble(destAddr + 64, m.m22);
            putDouble(destAddr + 72, m.m30);
            putDouble(destAddr + 80, m.m31);
            putDouble(destAddr + 88, m.m32);
        }

        public void putTransposed(Matrix4d m, long destAddr) {
            putDouble(destAddr,       m.m00);
            putDouble(destAddr + 8,   m.m10);
            putDouble(destAddr + 16,  m.m20);
            putDouble(destAddr + 24,  m.m30);
            putDouble(destAddr + 32,  m.m01);
            putDouble(destAddr + 40,  m.m11);
            putDouble(destAddr + 48,  m.m21);
            putDouble(destAddr + 56,  m.m31);
            putDouble(destAddr + 64,  m.m02);
            putDouble(destAddr + 72,  m.m12);
            putDouble(destAddr + 80,  m.m22);
            putDouble(destAddr + 88,  m.m32);
            putDouble(destAddr + 96,  m.m03);
            putDouble(destAddr + 104, m.m13);
            putDouble(destAddr + 112, m.m23);
            putDouble(destAddr + 120, m.m33);
        }

        public void putfTransposed(Matrix4d m, long destAddr) {
            putFloat(destAddr,      (float)m.m00);
            putFloat(destAddr + 4,  (float)m.m10);
            putFloat(destAddr + 8,  (float)m.m20);
            putFloat(destAddr + 12, (float)m.m30);
            putFloat(destAddr + 16, (float)m.m01);
            putFloat(destAddr + 20, (float)m.m11);
            putFloat(destAddr + 24, (float)m.m21);
            putFloat(destAddr + 28, (float)m.m31);
            putFloat(destAddr + 32, (float)m.m02);
            putFloat(destAddr + 36, (float)m.m12);
            putFloat(destAddr + 40, (float)m.m22);
            putFloat(destAddr + 44, (float)m.m32);
            putFloat(destAddr + 48, (float)m.m03);
            putFloat(destAddr + 52, (float)m.m13);
            putFloat(destAddr + 56, (float)m.m23);
            putFloat(destAddr + 60, (float)m.m33);
        }

        public void put4x3Transposed(Matrix4d m, long destAddr) {
            putDouble(destAddr,      m.m00);
            putDouble(destAddr + 8,  m.m10);
            putDouble(destAddr + 16, m.m20);
            putDouble(destAddr + 24, m.m30);
            putDouble(destAddr + 32, m.m01);
            putDouble(destAddr + 40, m.m11);
            putDouble(destAddr + 48, m.m21);
            putDouble(destAddr + 56, m.m31);
            putDouble(destAddr + 64, m.m02);
            putDouble(destAddr + 72, m.m12);
            putDouble(destAddr + 80, m.m22);
            putDouble(destAddr + 88, m.m32);
        }

        public void putTransposed(Matrix4x3d m, long destAddr) {
            putDouble(destAddr,      m.m00);
            putDouble(destAddr + 8,  m.m10);
            putDouble(destAddr + 16, m.m20);
            putDouble(destAddr + 24, m.m30);
            putDouble(destAddr + 32, m.m01);
            putDouble(destAddr + 40, m.m11);
            putDouble(destAddr + 48, m.m21);
            putDouble(destAddr + 56, m.m31);
            putDouble(destAddr + 64, m.m02);
            putDouble(destAddr + 72, m.m12);
            putDouble(destAddr + 80, m.m22);
            putDouble(destAddr + 88, m.m32);
        }

        public void putfTransposed(Matrix4x3d m, long destAddr) {
            putFloat(destAddr,      (float)m.m00);
            putFloat(destAddr + 4,  (float)m.m10);
            putFloat(destAddr + 8,  (float)m.m20);
            putFloat(destAddr + 12, (float)m.m30);
            putFloat(destAddr + 16, (float)m.m01);
            putFloat(destAddr + 20, (float)m.m11);
            putFloat(destAddr + 24, (float)m.m21);
            putFloat(destAddr + 28, (float)m.m31);
            putFloat(destAddr + 32, (float)m.m02);
            putFloat(destAddr + 36, (float)m.m12);
            putFloat(destAddr + 40, (float)m.m22);
            putFloat(destAddr + 44, (float)m.m32);
        }

        public void putf(Matrix4d m, long destAddr) {
            putFloat(destAddr,      (float)m.m00);
            putFloat(destAddr + 4,  (float)m.m01);
            putFloat(destAddr + 8,  (float)m.m02);
            putFloat(destAddr + 12, (float)m.m03);
            putFloat(destAddr + 16, (float)m.m10);
            putFloat(destAddr + 20, (float)m.m11);
            putFloat(destAddr + 24, (float)m.m12);
            putFloat(destAddr + 28, (float)m.m13);
            putFloat(destAddr + 32, (float)m.m20);
            putFloat(destAddr + 36, (float)m.m21);
            putFloat(destAddr + 40, (float)m.m22);
            putFloat(destAddr + 44, (float)m.m23);
            putFloat(destAddr + 48, (float)m.m30);
            putFloat(destAddr + 52, (float)m.m31);
            putFloat(destAddr + 56, (float)m.m32);
            putFloat(destAddr + 60, (float)m.m33);
        }

        public void putf(Matrix4x3d m, long destAddr) {
            putFloat(destAddr,      (float)m.m00);
            putFloat(destAddr + 4,  (float)m.m01);
            putFloat(destAddr + 8,  (float)m.m02);
            putFloat(destAddr + 12, (float)m.m10);
            putFloat(destAddr + 16, (float)m.m11);
            putFloat(destAddr + 20, (float)m.m12);
            putFloat(destAddr + 24, (float)m.m20);
            putFloat(destAddr + 28, (float)m.m21);
            putFloat(destAddr + 32, (float)m.m22);
            putFloat(destAddr + 36, (float)m.m30);
            putFloat(destAddr + 40, (float)m.m31);
            putFloat(destAddr + 44, (float)m.m32);
        }

        public void put(Matrix3f m, long destAddr) {
            putFloat(destAddr,    m.m00);
            putFloat(destAddr+4,  m.m01);
            putFloat(destAddr+8,  m.m02);
            putFloat(destAddr+12, m.m10);
            putFloat(destAddr+16, m.m11);
            putFloat(destAddr+20, m.m12);
            putFloat(destAddr+24, m.m20);
            putFloat(destAddr+28, m.m21);
            putFloat(destAddr+32, m.m22);
        }

        public void put(Matrix3d m, long destAddr) {
            putDouble(destAddr,      m.m00);
            putDouble(destAddr + 8,  m.m01);
            putDouble(destAddr + 16, m.m02);
            putDouble(destAddr + 24, m.m10);
            putDouble(destAddr + 32, m.m11);
            putDouble(destAddr + 40, m.m12);
            putDouble(destAddr + 48, m.m20);
            putDouble(destAddr + 56, m.m21);
            putDouble(destAddr + 64, m.m22);
        }

        public void put(Matrix3x2f m, long destAddr) {
            putFloat(destAddr,      m.m00);
            putFloat(destAddr + 4,  m.m01);
            putFloat(destAddr + 8,  m.m10);
            putFloat(destAddr + 12, m.m11);
            putFloat(destAddr + 16, m.m20);
            putFloat(destAddr + 20, m.m21);
        }

        public void put(Matrix3x2d m, long destAddr) {
            putDouble(destAddr,      m.m00);
            putDouble(destAddr + 8,  m.m01);
            putDouble(destAddr + 16, m.m10);
            putDouble(destAddr + 24, m.m11);
            putDouble(destAddr + 32, m.m20);
            putDouble(destAddr + 40, m.m21);
        }

        public void putf(Matrix3d m, long destAddr) {
            putFloat(destAddr,      (float)m.m00);
            putFloat(destAddr + 4,  (float)m.m01);
            putFloat(destAddr + 8,  (float)m.m02);
            putFloat(destAddr + 12, (float)m.m10);
            putFloat(destAddr + 16, (float)m.m11);
            putFloat(destAddr + 20, (float)m.m12);
            putFloat(destAddr + 24, (float)m.m20);
            putFloat(destAddr + 28, (float)m.m21);
            putFloat(destAddr + 32, (float)m.m22);
        }

        public void put(Vector4d src, long destAddr) {
            putDouble(destAddr, src.x);
            putDouble(destAddr+8, src.y);
            putDouble(destAddr+16, src.z);
            putDouble(destAddr+24, src.w);
        }

        public void put(Vector4f src, long destAddr) {
            putFloat(destAddr, src.x);
            putFloat(destAddr+4, src.y);
            putFloat(destAddr+8, src.z);
            putFloat(destAddr+12, src.w);
        }

        public void put(Vector4i src, long destAddr) {
            UNSAFE.putInt(null, destAddr, src.x);
            UNSAFE.putInt(null, destAddr+4, src.y);
            UNSAFE.putInt(null, destAddr+8, src.z);
            UNSAFE.putInt(null, destAddr+12, src.w);
        }

        public void put(Vector3f src, long destAddr) {
            putFloat(destAddr,   src.x);
            putFloat(destAddr+4, src.y);
            putFloat(destAddr+8, src.z);
        }

        public void put(Vector3d src, long destAddr) {
            putDouble(destAddr,    src.x);
            putDouble(destAddr+8,  src.y);
            putDouble(destAddr+16, src.z);
        }

        public void put(Vector3i src, long destAddr) {
            UNSAFE.putInt(null, destAddr,   src.x);
            UNSAFE.putInt(null, destAddr+4, src.y);
            UNSAFE.putInt(null, destAddr+8, src.z);
        }

        public void put(Vector2f src, long destAddr) {
            putFloat(destAddr,   src.x);
            putFloat(destAddr+4, src.y);
        }

        public void put(Vector2d src, long destAddr) {
            putDouble(destAddr,   src.x);
            putDouble(destAddr+8, src.y);
        }

        public void put(Vector2i src, long destAddr) {
            UNSAFE.putInt(null, destAddr,   src.x);
            UNSAFE.putInt(null, destAddr+4, src.y);
        }

        public void get(Matrix4f m, long srcAddr) {
            for (int i = 0; i < 8; i++) {
                UNSAFE.putLong(m, Matrix4f_m00 + (i << 3), UNSAFE.getLong(null, srcAddr + (i << 3)));
            }
        }

        public void get(Matrix4x3f m, long srcAddr) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putLong(m, Matrix4x3f_m00 + (i << 3), UNSAFE.getLong(null, srcAddr + (i << 3)));
            }
        }

        public void get(Matrix4d m, long srcAddr) {
            m.m00 = getDouble(srcAddr);
            m.m01 = getDouble(srcAddr+8);
            m.m02 = getDouble(srcAddr+16);
            m.m03 = getDouble(srcAddr+24);
            m.m10 = getDouble(srcAddr+32);
            m.m11 = getDouble(srcAddr+40);
            m.m12 = getDouble(srcAddr+48);
            m.m13 = getDouble(srcAddr+56);
            m.m20 = getDouble(srcAddr+64);
            m.m21 = getDouble(srcAddr+72);
            m.m22 = getDouble(srcAddr+80);
            m.m23 = getDouble(srcAddr+88);
            m.m30 = getDouble(srcAddr+96);
            m.m31 = getDouble(srcAddr+104);
            m.m32 = getDouble(srcAddr+112);
            m.m33 = getDouble(srcAddr+120);
        }

        public void get(Matrix4x3d m, long srcAddr) {
            m.m00 = getDouble(srcAddr);
            m.m01 = getDouble(srcAddr+8);
            m.m02 = getDouble(srcAddr+16);
            m.m10 = getDouble(srcAddr+24);
            m.m11 = getDouble(srcAddr+32);
            m.m12 = getDouble(srcAddr+40);
            m.m20 = getDouble(srcAddr+48);
            m.m21 = getDouble(srcAddr+56);
            m.m22 = getDouble(srcAddr+64);
            m.m30 = getDouble(srcAddr+72);
            m.m31 = getDouble(srcAddr+80);
            m.m32 = getDouble(srcAddr+88);
        }

        public void getf(Matrix4d m, long srcAddr) {
            m.m00 = getFloat(srcAddr);
            m.m01 = getFloat(srcAddr+4);
            m.m02 = getFloat(srcAddr+8);
            m.m03 = getFloat(srcAddr+12);
            m.m10 = getFloat(srcAddr+16);
            m.m11 = getFloat(srcAddr+20);
            m.m12 = getFloat(srcAddr+24);
            m.m13 = getFloat(srcAddr+28);
            m.m20 = getFloat(srcAddr+32);
            m.m21 = getFloat(srcAddr+36);
            m.m22 = getFloat(srcAddr+40);
            m.m23 = getFloat(srcAddr+44);
            m.m30 = getFloat(srcAddr+48);
            m.m31 = getFloat(srcAddr+52);
            m.m32 = getFloat(srcAddr+56);
            m.m33 = getFloat(srcAddr+60);
        }

        public void getf(Matrix4x3d m, long srcAddr) {
            m.m00 = getFloat(srcAddr);
            m.m01 = getFloat(srcAddr+4);
            m.m02 = getFloat(srcAddr+8);
            m.m10 = getFloat(srcAddr+12);
            m.m11 = getFloat(srcAddr+16);
            m.m12 = getFloat(srcAddr+20);
            m.m20 = getFloat(srcAddr+24);
            m.m21 = getFloat(srcAddr+28);
            m.m22 = getFloat(srcAddr+32);
            m.m30 = getFloat(srcAddr+36);
            m.m31 = getFloat(srcAddr+40);
            m.m32 = getFloat(srcAddr+44);
        }

        public void get(Matrix3f m, long srcAddr) {
            m.m00 = getFloat(srcAddr);
            m.m01 = getFloat(srcAddr+4);
            m.m02 = getFloat(srcAddr+8);
            m.m10 = getFloat(srcAddr+12);
            m.m11 = getFloat(srcAddr+16);
            m.m12 = getFloat(srcAddr+20);
            m.m20 = getFloat(srcAddr+24);
            m.m21 = getFloat(srcAddr+28);
            m.m22 = getFloat(srcAddr+32);
        }

        public void get(Matrix3d m, long srcAddr) {
            m.m00 = getDouble(srcAddr);
            m.m01 = getDouble(srcAddr+8);
            m.m02 = getDouble(srcAddr+16);
            m.m10 = getDouble(srcAddr+24);
            m.m11 = getDouble(srcAddr+32);
            m.m12 = getDouble(srcAddr+40);
            m.m20 = getDouble(srcAddr+48);
            m.m21 = getDouble(srcAddr+56);
            m.m22 = getDouble(srcAddr+64);
        }

        public void get(Matrix3x2f m, long srcAddr) {
            m.m00 = getFloat(srcAddr);
            m.m01 = getFloat(srcAddr+4);
            m.m10 = getFloat(srcAddr+8);
            m.m11 = getFloat(srcAddr+12);
            m.m20 = getFloat(srcAddr+16);
            m.m21 = getFloat(srcAddr+20);
        }

        public void get(Matrix3x2d m, long srcAddr) {
            m.m00 = getDouble(srcAddr);
            m.m01 = getDouble(srcAddr+8);
            m.m10 = getDouble(srcAddr+16);
            m.m11 = getDouble(srcAddr+24);
            m.m20 = getDouble(srcAddr+32);
            m.m21 = getDouble(srcAddr+40);
        }

        public void getf(Matrix3d m, long srcAddr) {
            m.m00 = getFloat(srcAddr);
            m.m01 = getFloat(srcAddr+4);
            m.m02 = getFloat(srcAddr+8);
            m.m10 = getFloat(srcAddr+12);
            m.m11 = getFloat(srcAddr+16);
            m.m12 = getFloat(srcAddr+20);
            m.m20 = getFloat(srcAddr+24);
            m.m21 = getFloat(srcAddr+28);
            m.m22 = getFloat(srcAddr+32);
        }

        public void get(Vector4d dst, long srcAddr) {
            dst.x = getDouble(srcAddr);
            dst.y = getDouble(srcAddr+8);
            dst.z = getDouble(srcAddr+16);
            dst.w = getDouble(srcAddr+24);
        }

        public void get(Vector4f dst, long srcAddr) {
            dst.x = getFloat(srcAddr);
            dst.y = getFloat(srcAddr+4);
            dst.z = getFloat(srcAddr+8);
            dst.w = getFloat(srcAddr+12);
        }

        public void get(Vector4i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
            dst.z = UNSAFE.getInt(null, srcAddr+8);
            dst.w = UNSAFE.getInt(null, srcAddr+12);
        }

        public void get(Vector3f dst, long srcAddr) {
            dst.x = getFloat(srcAddr);
            dst.y = getFloat(srcAddr+4);
            dst.z = getFloat(srcAddr+8);
        }

        public void get(Vector3d dst, long srcAddr) {
            dst.x = getDouble(srcAddr);
            dst.y = getDouble(srcAddr+8);
            dst.z = getDouble(srcAddr+16);
        }

        public void get(Vector3i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
            dst.z = UNSAFE.getInt(null, srcAddr+8);
        }

        public void get(Vector2f dst, long srcAddr) {
            dst.x = getFloat(srcAddr);
            dst.y = getFloat(srcAddr+4);
        }

        public void get(Vector2d dst, long srcAddr) {
            dst.x = getDouble(srcAddr);
            dst.y = getDouble(srcAddr+8);
        }

        public void get(Vector2i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
        }

        private void putMatrix3f(Quaternionf q, long addr) {
            float dx = q.x + q.x;
            float dy = q.y + q.y;
            float dz = q.z + q.z;
            float q00 = dx * q.x;
            float q11 = dy * q.y;
            float q22 = dz * q.z;
            float q01 = dx * q.y;
            float q02 = dx * q.z;
            float q03 = dx * q.w;
            float q12 = dy * q.z;
            float q13 = dy * q.w;
            float q23 = dz * q.w;
            putFloat(addr, 1.0f - q11 - q22);
            putFloat(addr + 4, q01 + q23);
            putFloat(addr + 8, q02 - q13);
            putFloat(addr + 12, q01 - q23);
            putFloat(addr + 16, 1.0f - q22 - q00);
            putFloat(addr + 20, q12 + q03);
            putFloat(addr + 24, q02 + q13);
            putFloat(addr + 28, q12 - q03);
            putFloat(addr + 32, 1.0f - q11 - q00); 
        }

        private void putMatrix4f(Quaternionf q, long addr) {
            float dx = q.x + q.x;
            float dy = q.y + q.y;
            float dz = q.z + q.z;
            float q00 = dx * q.x;
            float q11 = dy * q.y;
            float q22 = dz * q.z;
            float q01 = dx * q.y;
            float q02 = dx * q.z;
            float q03 = dx * q.w;
            float q12 = dy * q.z;
            float q13 = dy * q.w;
            float q23 = dz * q.w;
            putFloat(addr, 1.0f - q11 - q22);
            putFloat(addr + 4, q01 + q23);
            UNSAFE.putLong(null, addr + 8, Float.floatToRawIntBits(q02 - q13) & 0xFFFFFFFFL);
            putFloat(addr + 16, q01 - q23);
            putFloat(addr + 20, 1.0f - q22 - q00);
            UNSAFE.putLong(null, addr + 24, Float.floatToRawIntBits(q12 + q03) & 0xFFFFFFFFL);
            putFloat(addr + 32, q02 + q13);
            putFloat(addr + 36, q12 - q03);
            UNSAFE.putLong(null, addr + 40, Float.floatToRawIntBits(1.0f - q11 - q00) & 0xFFFFFFFFL);
            UNSAFE.putLong(null, addr + 48, 0L);
            UNSAFE.putLong(null, addr + 56, 0x3F80000000000000L);
        }

        private void putMatrix4x3f(Quaternionf q, long addr) {
            float dx = q.x + q.x;
            float dy = q.y + q.y;
            float dz = q.z + q.z;
            float q00 = dx * q.x;
            float q11 = dy * q.y;
            float q22 = dz * q.z;
            float q01 = dx * q.y;
            float q02 = dx * q.z;
            float q03 = dx * q.w;
            float q12 = dy * q.z;
            float q13 = dy * q.w;
            float q23 = dz * q.w;
            putFloat(addr, 1.0f - q11 - q22);
            putFloat(addr + 4, q01 + q23);
            putFloat(addr + 8, q02 - q13);
            putFloat(addr + 12, q01 - q23);
            putFloat(addr + 16, 1.0f - q22 - q00);
            putFloat(addr + 20, q12 + q03);
            putFloat(addr + 24, q02 + q13);
            putFloat(addr + 28, q12 - q03);
            putFloat(addr + 32, 1.0f - q11 - q00);
            UNSAFE.putLong(null, addr + 36, 0L);
            putFloat(addr + 44, 0.0f);
        }

        private static void throwNoDirectBufferException() {
            throw new IllegalArgumentException("Must use a direct buffer");
        }

        public void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix3f(q, addr);
        }

        public void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix3f(q, addr);
        }

        public void putMatrix4f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix4f(q, addr);
        }

        public void putMatrix4f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix4f(q, addr);
        }

        public void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix4x3f(q, addr);
        }

        public void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix4x3f(q, addr);
        }

        public void put(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 3));
        }

        public void put4x4(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix3x2f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix3x2f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix3x2d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + (offset << 2));
        }

        public void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void putf(Matrix4d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        public void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + (offset << 3));
        }

        public void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        public void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + offset);
        }

        public void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + offset);
        }

        public void put(Matrix3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3x2f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3x2f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3x2d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3x2d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void putf(Matrix3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void put(Vector4d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector4d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector4f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector4f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector4i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector4i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector3f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector3d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector3i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector2f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector2d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector2i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void get(Matrix4f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix4f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4x3f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix4x3f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix4d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix4x3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void getf(Matrix4d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix4d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void get(Matrix3f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3x2f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3x2f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3x2d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3x2d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void getf(Matrix3d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void get(Vector4d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector4d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector4f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector4f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector4i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector4i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector3f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector3d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector3i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector2f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector2d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector2i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }
    }

    public static class MemUtilUnsafe_Marshmallow extends MemUtilUnsafe_Nougat {
        public void put(Matrix4f m, long destAddr) {
            for (int i = 0; i < 8; i++) {
                UNSAFE.putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4f_m00 + (i << 3)));
            }
        }

        public void put(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4x3f_m00 + (i << 3)));
            }
        }

        public void put(Matrix3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3f_m00 + (i << 3)));
            }
            putFloat(destAddr + 32, m.m22);
        }

        public void put(Matrix3x2f m, long destAddr) {
            for (int i = 0; i < 3; i++) {
                UNSAFE.putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3x2f_m00 + (i << 3)));
            }
        }

    }
}
