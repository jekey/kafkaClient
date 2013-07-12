/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.babyduncan.mykafka.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Domainsync implements org.apache.thrift.TBase<Domainsync, Domainsync._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Domainsync");

  private static final org.apache.thrift.protocol.TField DOMAIN_FIELD_DESC = new org.apache.thrift.protocol.TField("domain", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PASSPORT_FIELD_DESC = new org.apache.thrift.protocol.TField("passport", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SID_FIELD_DESC = new org.apache.thrift.protocol.TField("sid", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DomainsyncStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DomainsyncTupleSchemeFactory());
  }

  public String domain; // required
  public int type; // required
  public String passport; // required
  public long sid; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DOMAIN((short)1, "domain"),
    TYPE((short)2, "type"),
    PASSPORT((short)3, "passport"),
    SID((short)4, "sid");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DOMAIN
          return DOMAIN;
        case 2: // TYPE
          return TYPE;
        case 3: // PASSPORT
          return PASSPORT;
        case 4: // SID
          return SID;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TYPE_ISSET_ID = 0;
  private static final int __SID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DOMAIN, new org.apache.thrift.meta_data.FieldMetaData("domain", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PASSPORT, new org.apache.thrift.meta_data.FieldMetaData("passport", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SID, new org.apache.thrift.meta_data.FieldMetaData("sid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Domainsync.class, metaDataMap);
  }

  public Domainsync() {
  }

  public Domainsync(
    String domain,
    int type,
    String passport,
    long sid)
  {
    this();
    this.domain = domain;
    this.type = type;
    setTypeIsSet(true);
    this.passport = passport;
    this.sid = sid;
    setSidIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Domainsync(Domainsync other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDomain()) {
      this.domain = other.domain;
    }
    this.type = other.type;
    if (other.isSetPassport()) {
      this.passport = other.passport;
    }
    this.sid = other.sid;
  }

  public Domainsync deepCopy() {
    return new Domainsync(this);
  }

  @Override
  public void clear() {
    this.domain = null;
    setTypeIsSet(false);
    this.type = 0;
    this.passport = null;
    setSidIsSet(false);
    this.sid = 0;
  }

  public String getDomain() {
    return this.domain;
  }

  public Domainsync setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public void unsetDomain() {
    this.domain = null;
  }

  /** Returns true if field domain is set (has been assigned a value) and false otherwise */
  public boolean isSetDomain() {
    return this.domain != null;
  }

  public void setDomainIsSet(boolean value) {
    if (!value) {
      this.domain = null;
    }
  }

  public int getType() {
    return this.type;
  }

  public Domainsync setType(int type) {
    this.type = type;
    setTypeIsSet(true);
    return this;
  }

  public void unsetType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return EncodingUtils.testBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  public void setTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TYPE_ISSET_ID, value);
  }

  public String getPassport() {
    return this.passport;
  }

  public Domainsync setPassport(String passport) {
    this.passport = passport;
    return this;
  }

  public void unsetPassport() {
    this.passport = null;
  }

  /** Returns true if field passport is set (has been assigned a value) and false otherwise */
  public boolean isSetPassport() {
    return this.passport != null;
  }

  public void setPassportIsSet(boolean value) {
    if (!value) {
      this.passport = null;
    }
  }

  public long getSid() {
    return this.sid;
  }

  public Domainsync setSid(long sid) {
    this.sid = sid;
    setSidIsSet(true);
    return this;
  }

  public void unsetSid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SID_ISSET_ID);
  }

  /** Returns true if field sid is set (has been assigned a value) and false otherwise */
  public boolean isSetSid() {
    return EncodingUtils.testBit(__isset_bitfield, __SID_ISSET_ID);
  }

  public void setSidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DOMAIN:
      if (value == null) {
        unsetDomain();
      } else {
        setDomain((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((Integer)value);
      }
      break;

    case PASSPORT:
      if (value == null) {
        unsetPassport();
      } else {
        setPassport((String)value);
      }
      break;

    case SID:
      if (value == null) {
        unsetSid();
      } else {
        setSid((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DOMAIN:
      return getDomain();

    case TYPE:
      return Integer.valueOf(getType());

    case PASSPORT:
      return getPassport();

    case SID:
      return Long.valueOf(getSid());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DOMAIN:
      return isSetDomain();
    case TYPE:
      return isSetType();
    case PASSPORT:
      return isSetPassport();
    case SID:
      return isSetSid();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Domainsync)
      return this.equals((Domainsync)that);
    return false;
  }

  public boolean equals(Domainsync that) {
    if (that == null)
      return false;

    boolean this_present_domain = true && this.isSetDomain();
    boolean that_present_domain = true && that.isSetDomain();
    if (this_present_domain || that_present_domain) {
      if (!(this_present_domain && that_present_domain))
        return false;
      if (!this.domain.equals(that.domain))
        return false;
    }

    boolean this_present_type = true;
    boolean that_present_type = true;
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (this.type != that.type)
        return false;
    }

    boolean this_present_passport = true && this.isSetPassport();
    boolean that_present_passport = true && that.isSetPassport();
    if (this_present_passport || that_present_passport) {
      if (!(this_present_passport && that_present_passport))
        return false;
      if (!this.passport.equals(that.passport))
        return false;
    }

    boolean this_present_sid = true;
    boolean that_present_sid = true;
    if (this_present_sid || that_present_sid) {
      if (!(this_present_sid && that_present_sid))
        return false;
      if (this.sid != that.sid)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Domainsync other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Domainsync typedOther = (Domainsync)other;

    lastComparison = Boolean.valueOf(isSetDomain()).compareTo(typedOther.isSetDomain());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomain()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domain, typedOther.domain);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPassport()).compareTo(typedOther.isSetPassport());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassport()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.passport, typedOther.passport);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSid()).compareTo(typedOther.isSetSid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sid, typedOther.sid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Domainsync(");
    boolean first = true;

    sb.append("domain:");
    if (this.domain == null) {
      sb.append("null");
    } else {
      sb.append(this.domain);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    sb.append(this.type);
    first = false;
    if (!first) sb.append(", ");
    sb.append("passport:");
    if (this.passport == null) {
      sb.append("null");
    } else {
      sb.append(this.passport);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sid:");
    sb.append(this.sid);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DomainsyncStandardSchemeFactory implements SchemeFactory {
    public DomainsyncStandardScheme getScheme() {
      return new DomainsyncStandardScheme();
    }
  }

  private static class DomainsyncStandardScheme extends StandardScheme<Domainsync> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Domainsync struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DOMAIN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.domain = iprot.readString();
              struct.setDomainIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = iprot.readI32();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PASSPORT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.passport = iprot.readString();
              struct.setPassportIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sid = iprot.readI64();
              struct.setSidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Domainsync struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.domain != null) {
        oprot.writeFieldBegin(DOMAIN_FIELD_DESC);
        oprot.writeString(struct.domain);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TYPE_FIELD_DESC);
      oprot.writeI32(struct.type);
      oprot.writeFieldEnd();
      if (struct.passport != null) {
        oprot.writeFieldBegin(PASSPORT_FIELD_DESC);
        oprot.writeString(struct.passport);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SID_FIELD_DESC);
      oprot.writeI64(struct.sid);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DomainsyncTupleSchemeFactory implements SchemeFactory {
    public DomainsyncTupleScheme getScheme() {
      return new DomainsyncTupleScheme();
    }
  }

  private static class DomainsyncTupleScheme extends TupleScheme<Domainsync> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Domainsync struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetDomain()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      if (struct.isSetPassport()) {
        optionals.set(2);
      }
      if (struct.isSetSid()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetDomain()) {
        oprot.writeString(struct.domain);
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type);
      }
      if (struct.isSetPassport()) {
        oprot.writeString(struct.passport);
      }
      if (struct.isSetSid()) {
        oprot.writeI64(struct.sid);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Domainsync struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.domain = iprot.readString();
        struct.setDomainIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = iprot.readI32();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.passport = iprot.readString();
        struct.setPassportIsSet(true);
      }
      if (incoming.get(3)) {
        struct.sid = iprot.readI64();
        struct.setSidIsSet(true);
      }
    }
  }

}

