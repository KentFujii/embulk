//
// MessagePack for Java
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.msgpack.value;

import java.math.BigInteger;

/**
 * Representation of MessagePack's Integer type.
 *
 * MessagePack's Integer type can represent from -2<sup>63</sup> to 2<sup>64</sup>-1.
 */
public final class IntegerValue
        implements NumberValue
{
    private final long value;

    public IntegerValue(long value)
    {
        this.value = value;
    }

    private static final long BYTE_MIN = (long) Byte.MIN_VALUE;
    private static final long BYTE_MAX = (long) Byte.MAX_VALUE;
    private static final long SHORT_MIN = (long) Short.MIN_VALUE;
    private static final long SHORT_MAX = (long) Short.MAX_VALUE;
    private static final long INT_MIN = (long) Integer.MIN_VALUE;
    private static final long INT_MAX = (long) Integer.MAX_VALUE;

    @Override
    public ValueType getValueType()
    {
        return ValueType.INTEGER;
    }

    @Override
    public NumberValue asNumberValue()
    {
        return this;
    }

    @Override
    public IntegerValue asIntegerValue()
    {
        return this;
    }

    /**
     * Returns true if the value is in the range of [-2<sup>7</sup> to 2<sup>7</sup>-1].
     */
    public boolean isInByteRange()
    {
        return BYTE_MIN <= value && value <= BYTE_MAX;
    }

    /**
     * Returns true if the value is in the range of [-2<sup>15</sup> to 2<sup>15</sup>-1]
     */
    public boolean isInShortRange()
    {
        return SHORT_MIN <= value && value <= SHORT_MAX;
    }

    /**
     * Returns true if the value is in the range of [-2<sup>31</sup> to 2<sup>31</sup>-1]
     */
    public boolean isInIntRange()
    {
        return INT_MIN <= value && value <= INT_MAX;
    }

    /**
     * Returns true if the value is in the range of [-2<sup>63</sup> to 2<sup>63</sup>-1]
     */
    public boolean isInLongRange()
    {
        return true;
    }

    /**
     * Returns the value as a {@code byte}, otherwise throws an exception.
     *
     * @throws MessageIntegerOverflowException If the value does not fit in the range of {@code byte} type.
     */
    public byte asByte()
    {
        if (!isInByteRange()) {
            throw new MessageIntegerOverflowException(value);
        }
        return (byte) value;
    }

    /**
     * Returns the value as a {@code short}, otherwise throws an exception.
     *
     * @throws MessageIntegerOverflowException If the value does not fit in the range of {@code short} type.
     */
    public short asShort()
    {
        if (!isInShortRange()) {
            throw new MessageIntegerOverflowException(value);
        }
        return (short) value;
    }

    /**
     * Returns the value as an {@code int}, otherwise throws an exception.
     *
     * @throws MessageIntegerOverflowException If the value does not fit in the range of {@code int} type.
     */
    public int asInt()
    {
        if (!isInIntRange()) {
            throw new MessageIntegerOverflowException(value);
        }
        return (int) value;
    }

    /**
     * Returns the value as a {@code long}, otherwise throws an exception.
     *
     * @throws MessageIntegerOverflowException If the value does not fit in the range of {@code long} type.
     */
    public long asLong()
    {
        return value;
    }

    /**
     * Returns the value as a {@code BigInteger}.
     */
    public BigInteger asBigInteger()
    {
        return BigInteger.valueOf((long) value);
    }

    @Override
    public byte toByte()
    {
        return (byte) value;
    }

    @Override
    public short toShort()
    {
        return (short) value;
    }

    @Override
    public int toInt()
    {
        return (int) value;
    }

    @Override
    public long toLong()
    {
        return value;
    }

    @Override
    public BigInteger toBigInteger()
    {
        return BigInteger.valueOf(value);
    }

    @Override
    public float toFloat()
    {
        return (float) value;
    }

    @Override
    public double toDouble()
    {
        return (double) value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Value)) {
            return false;
        }
        Value v = (Value) o;
        if (!v.isIntegerValue()) {
            return false;
        }

        IntegerValue iv = v.asIntegerValue();
        if (!iv.isInLongRange()) {
            return false;
        }
        return value == iv.toLong();
    }

    @Override
    public int hashCode()
    {
        if (INT_MIN <= value && value <= INT_MAX) {
            return (int) value;
        }
        else {
            return (int) (value ^ (value >>> 32));
        }
    }

    @Override
    public String toJson()
    {
        return Long.toString(value);
    }

    @Override
    public String toString()
    {
        return toJson();
    }
}
